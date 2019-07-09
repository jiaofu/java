package com.shuiliu.blockchain.first;

import com.alibaba.fastjson.JSONObject;
import com.shuiliu.blockchain.BlockChain;
import com.shuiliu.blockchain.Proof;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class FirstController {
    @GetMapping("time")
    public long time(){
        return System.currentTimeMillis();
    }

    /**
     * 一致性共识算法，解决共识冲突，保证所有的节点都在同一条链上(最长链)
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("/nodes/resolve")
    public void resolve(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BlockChain blockChain = BlockChain.getInstance();
        boolean flag = blockChain.resolveConflicts();
        System.out.println("是否解决一致性共识冲突：" + flag);
    }

    /**
     * 该Servlet用于输出整个区块链的数据(Json)
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("/chain")
    public Map<String,Object> chain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BlockChain blockChain = BlockChain.getInstance();
        Map<String,Object> response = new HashMap<String, Object>();
        response.put("chain",blockChain.getChain());
        response.put("blockLength",blockChain.getChain().size());
        return response;
    }

    /**
     * 该Servlet用于运行工作算法的证明来获得下一个证明，也就是所谓的挖矿
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("/mine")
    protected  Map<String,Object>  doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BlockChain blockChain = BlockChain.getInstance();

        //计算出工作量证明
        Map<String,Object> lastBlock = blockChain.lastBlock();
        Long last_proof = Long.parseLong(lastBlock.get("proof") + "");
        Long proof = new Proof().ProofOfWork(last_proof);

        //奖励计算出工作量证明的矿工1个币的奖励，发送者为"0"表明这是新挖出的矿。
        String uuid = UUID.randomUUID().toString().replace("-", "");
        blockChain.newTransactions("0",uuid,1);

        //构建新的区块
        Map<String,Object> newBlock = blockChain.newBlock(proof,null);
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "New Block Forged");
        response.put("index", newBlock.get("index"));
        response.put("transactions", newBlock.get("transactions"));
        response.put("proof", newBlock.get("proof"));
        response.put("previous_hash", newBlock.get("previous_hash"));

        // 返回新区块的数据给客户端
        return response;
    }

    /**
     * 该Servlet用于接收并处理新的交易信息
     * @param sender
     * @param recipient
     * @param amount
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */

    @GetMapping("/transactions/new")
    protected JSONObject transactions(String sender,String recipient ,long amount, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 新建交易信息
        BlockChain blockChain = BlockChain.getInstance();
        int index = blockChain.newTransactions(sender, recipient, amount);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Transaction will be added to Block " + index);
        return jsonObject;
    }

    /**
     * 注册网络节点
     * @param nodes
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("/nodes/register")
    protected JSONObject register(String nodes, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BlockChain blockChain = BlockChain.getInstance();
        blockChain.registerNode(nodes);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "The Nodes is : " + blockChain.getNodes());
        return jsonObject;
    }

}
