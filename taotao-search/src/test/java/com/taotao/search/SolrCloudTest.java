package com.taotao.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrCloudTest {

	@Test
	public void testSolrCloud() throws SolrServerException {
		//参数就是zookeeper集群的地址列表。
		CloudSolrServer solrServer = new CloudSolrServer("192.168.25.154:2181,192.168.25.154:2182,192.168.25.154:2183");
		//设置默认的collection
		solrServer.setDefaultCollection("collection2");
		//查询索引库
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse response = solrServer.query(query);
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果总数量：" + solrDocumentList.getNumFound());
		
	}
}
