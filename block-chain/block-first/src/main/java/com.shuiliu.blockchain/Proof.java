package com.shuiliu.blockchain;

public class Proof {
    /**
     * 计算当前区块的工作量证明
     * @param last_proof 上一个区块的工作量证明
     * @return
     */
    public long ProofOfWork(long last_proof){
        long proof = 0;
        while (!(vaildProof(last_proof,proof))) {
            proof ++;
        }
        return proof;
    }

    /**
     * 验证证明，是否拼接后的Hash值以4个0开头
     * @param last_proof 上一个区块工作量证明
     * @param proof 当前区块的工作量证明
     * @return
     */
    public boolean vaildProof(long last_proof, long proof) {
        String guess = last_proof + "" + proof;
        String guess_hash = new Encrypt().Hash(guess);
        boolean flag = guess_hash.startsWith("0000");
        return  flag;
    }
}
