package com.chain.toychain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.alibaba.fastjson.JSON;
import com.hankcs.hanlp.HanLP;

@RestController
@EnableSwagger2
@Api(value = "区块链", description = "J2men测试用", tags = "Swagger Test Control Tag")
public class BlockChainRest {

	@Autowired
	private PeerToPeerNetwork peerToPeerNetwork;

	@ApiOperation(value = "获取区块链", response = String.class)
	@GetMapping(value = "/chain")
	public String getBlockChain() {
		BlockChain chain = peerToPeerNetwork.getChain();
		return chain.toString();
	}

	@ApiOperation(value = "根据peerId获取区块链", response = String.class)
	@GetMapping(value = "/chain/{peerId}")
	public String getPeerBlockChain(@PathVariable("peerId") String peerId) {
		BlockChain chainByPeer = peerToPeerNetwork.getChainByPeer(peerId);
		if (chainByPeer != null) {
			return chainByPeer.toString();
		} else {
			return "[]";
		}
	}

	@ApiOperation(value = "根据hash获取区块链", response = String.class)
	@GetMapping(value = "/block/{hash}")
	public String getBlockByHash(@PathVariable("hash") String hash) {
		Block block = peerToPeerNetwork.getChain().getBlockByHash(hash);
		if (block == null) {
			return "{}";
		}
		return JSON.toJSONString(block);
	}

	@ApiOperation(value = "根据peerId注册区块链", response = String.class)
	@PostMapping(value = "/register/{id}")
	public void register(@PathVariable("id") String id) {
		peerToPeerNetwork.register(id);
	}

	@ApiOperation(value = "根据peerId注销区块链", response = String.class)
	@PostMapping(value = "/out/{id}")
	public void out(@PathVariable("id") String id) {
		peerToPeerNetwork.out(id);
	}

	@ApiOperation(value = "根据peerId广播区块链", response = String.class)
	@PostMapping(value = "/broadcast/{id}")
	public String broadcast(@PathVariable("id") String id,
			@RequestBody String data) {
		BlockChain chainByPeer = peerToPeerNetwork.getChainByPeer(id);
		Block block = chainByPeer.assemblyNextBlock(data);
		boolean added = chainByPeer.addBlock(block);
		if (added) {
			peerToPeerNetwork.broadcast(id, block);
			return "{'status':'true'}";
		} else {
			return "{'status':'false'}";
		}
	}

	@ApiOperation(value = "根据peerId同步区块链", response = String.class)
	@PostMapping(value = "/peer/sync/{peerId}")
	public void syncPeer(@PathVariable("peerId") String peerId) {
		peerToPeerNetwork.syncPeer(peerId);
	}

	@ApiOperation(value = "分詞接口", response = String.class)
	@PostMapping(value = "/nlp/{reqId}")
	public String nlpWord(@PathVariable("reqId") String reqId,
			@RequestBody String data) {
		System.out.println(reqId + ":" + data);
		return HanLP.segment(data).toString();
	}
}
