package com.shuiliu.blockchain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class BlockChain {
    // 存储区块链
    private List<Map<String, Object>> chain;
    // 该实例变量用于当前的交易信息列表
    private List<Map<String, Object>> currentTransactions;
    // 网络中所有节点的集合
    private Set<String> nodes;

    private static BlockChain blockChain = null;

    private BlockChain() {
        // 初始化区块链以及当前的交易信息列表
        chain = new ArrayList<Map<String, Object>>();
        currentTransactions = new ArrayList<Map<String, Object>>();
        // 初始化存储网络中其他节点的集合
        nodes = new HashSet<String>();

        // 创建创世区块
        newBlock(100, "0");
    }

    /**
     * 在区块链上新建一个区块
     * @param proof 新区块的工作量证明
     * @param previous_hash 上一个区块的hash值
     * @return 返回新建的区块
     */
    public Map<String, Object> newBlock(long proof, String previous_hash) {

        Map<String, Object> block = new HashMap<String, Object>();
        block.put("index", getChain().size() + 1);
        block.put("timestamp", System.currentTimeMillis());
        block.put("transactions", getCurrentTransactions());
        block.put("proof", proof);
        // 如果没有传递上一个区块的hash就计算出区块链中最后一个区块的hash
        block.put("previous_hash", previous_hash != null ? previous_hash : hash(getChain().get(getChain().size() - 1)));

        // 重置当前的交易信息列表
        setCurrentTransactions(new ArrayList<Map<String, Object>>());

        getChain().add(block);

        return block;
    }

    // 创建单例对象
    public static BlockChain getInstance() {
        if (blockChain == null) {
            synchronized (BlockChain.class) {
                if (blockChain == null) {
                    blockChain = new BlockChain();
                }
            }
        }
        return blockChain;
    }

    /**
     * @return 得到区块链中的最后一个区块
     */
    public Map<String, Object> lastBlock() {
        return getChain().get(getChain().size() - 1);
    }

    /**
     * 生成新交易信息，信息将加入到下一个待挖的区块中
     * @param sender 发送方的地址
     * @param recipient 接收方的地址
     * @param amount 交易数量
     * @return 返回该交易事务的块的索引
     */
    public int newTransactions(String sender, String recipient, long amount) {

        Map<String, Object> transaction = new HashMap<String, Object>();
        transaction.put("sender", sender);
        transaction.put("recipient", recipient);
        transaction.put("amount", amount);

        getCurrentTransactions().add(transaction);

        return (Integer) lastBlock().get("index") + 1;
    }

    /**
     * 生成区块的 SHA-256格式的 hash值
     * @param block 区块
     * @return 返回该区块的hash
     */
    public static Object hash(Map<String, Object> block) {

        return new Encrypt().Hash( JSON.toJSONString(block));
    }

    /**
     * 注册节点
     * @param address 节点地址
     * @throws MalformedURLException
     */
    public void registerNode(String address) throws MalformedURLException {
        URL url = new URL(address);
        String node = url.getHost() + ":" + (url.getPort() == -1 ? url.getDefaultPort() : url.getPort());
        nodes.add(node);
    }

    /**
     * 验证是否为有效链,遍历每个区块验证hash和proof，来确定一个给定的区块链是否有效
     * @param chain
     * @return
     */
    public boolean vaildChain(List<Map<String,Object>> chain) {
        Map<String,Object> lastBlock = chain.get(0);
        int currentBlockIndex = 1;
        while (currentBlockIndex < lastBlock.size()) {
            Map<String,Object> currentBlock = chain.get(currentBlockIndex);
            //检查区块的hash是否正确
            if (!currentBlock.get("previous_hash").equals(hash(lastBlock))) {
                return false;
            }
            lastBlock = currentBlock;
            currentBlockIndex ++;
        }
        return true;
    }

    /**
     * 使用网络中最长的链. 遍历所有的邻居节点，并用上一个方法检查链的有效性，
     * 如果发现有效更长链，就替换掉自己的链
     * @return 如果链被取代返回true, 否则返回false
     * @throws IOException
     */
    public boolean resolveConflicts() throws IOException {
        //获得当前网络上所有的邻居节点
        Set<String> neighbours = this.nodes;

        List<Map<String, Object>> newChain = null;

        // 寻找最长的区块链0
        long maxLength = this.chain.size();

        // 获取并验证网络中的所有节点的区块链
        for (String node : neighbours) {

            URL url = new URL("http://" + node + "/chain");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() == 200) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuffer responseData = new StringBuffer();
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    responseData.append(response);
                }
                bufferedReader.close();

                JSONObject jsonData = JSONObject.parseObject(responseData.toString());
                long length = jsonData.getLong("blockLength");
                List<Map<String, Object>> chain = new ArrayList<>();
                List<Map> mapList = jsonData.getJSONArray("chain").toJavaList(Map.class);

                for(Map map : mapList){
                   Map<String,Object> stringObjectMap = (Map<String, Object>)map;
                    chain.add(stringObjectMap);
                }


                // 检查长度是否长，链是否有效
                if (length > maxLength && vaildChain(chain)) {
                    maxLength = length;
                    newChain = chain;
                }
            }

        }
        // 如果发现一个新的有效链比我们的长，就替换当前的链
        if (newChain != null) {
            this.chain = newChain;
            return true;
        }
        return false;
    }

    public List<Map<String, Object>> getChain() {
        return chain;
    }

    public void setChain(List<Map<String, Object>> chain) {
        this.chain = chain;
    }

    public List<Map<String, Object>> getCurrentTransactions() {
        return currentTransactions;
    }

    public void setCurrentTransactions(List<Map<String, Object>> currentTransactions) {
        this.currentTransactions = currentTransactions;
    }
    public Set<String> getNodes() {
        return nodes;
    }
    public void setNodes(Set<String> nodes) {
        this.nodes = nodes;
    }
}
