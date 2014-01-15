import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class TP2 {
	
static CharArraySet cas = new CharArraySet(Version.LUCENE_35, 0, true);


private static Type STRING;
    
	
	static final String[] TEST_STOP_WORDS = {
	    "a"
	  };
	
	 
	//HashSet cas = new HashSet();
	
	public static void testRehash() throws Exception {
		CharArraySet cas = new CharArraySet(Version.LUCENE_35, 0, true);
	    for(int i=0;i<TEST_STOP_WORDS.length;i++)
	      cas.add(TEST_STOP_WORDS[i]);
	  }
	
	public static void main(String[] args) throws IOException, ParseException {
		
		String s = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		s="l";
		while (!s.equalsIgnoreCase("q")) {
		      try {
		        System.out.println("Enter the search query (q=quit):");
		        if(!s.equalsIgnoreCase("q")){
		        s = br.readLine();
		        search(s);}
		        
		        if (s.equalsIgnoreCase("q")) {
		        	System.out.println("hello");
		          break;
		         
		        }
		      }catch (Exception e) {
		          System.out.println("Error searching " + s + " : " + e.getMessage());
		      }
		}
		    
		
	}

	@SuppressWarnings("deprecation")
	public static void search(String args) throws IOException, ParseException {
		
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_35, cas);
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35,analyzer);
		IndexWriter i = new IndexWriter(directory, config);
		String s = "";
		BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter corpus directory path.");
		s = b.readLine();
		
		File fdir = new File(s);
		File[] files = fdir.listFiles();
		
		
		for (File file : files) {
			Document document = new Document();
			String path = file.getCanonicalPath();
			BufferedReader bufr = new BufferedReader(new FileReader(path));
			int k=1;
			String ss;
			String bodystr="";
			while((ss = bufr.readLine())!=null)
			{
				if(k==1)
					document.add(new Field("category", ss.trim(), Field.Store.NO, Field.Index.NOT_ANALYZED));
				else if(k==2)
					document.add(new Field("sub_cat", ss.trim(), Field.Store.NO, Field.Index.NOT_ANALYZED));
				else
					bodystr.concat(ss).concat(" ");
				k++;
			}
			document.add(new Field("body", bodystr, Field.Store.YES, Field.Index.ANALYZED));
			//document.add(new Field("path", path, Field.Store.YES,Field.Index.ANALYZED));
			Reader reader = new FileReader(file);
			document.add(new Field("contents", reader));
			i.addDocument(document);
			bufr.close();
		}

		i.close();
		Query q = new QueryParser(Version.LUCENE_35, "contents", analyzer)
				.parse(args);
		
		
		// index search
		
		
		
		IndexReader reader = IndexReader.open(directory);
		int hitsPerPage = reader.numDocs();
		
		//System.out.print(reader.numDocs());
		
		IndexSearcher searcher = new IndexSearcher(reader);
		//TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		//System.out.print(reader.maxDoc()+"\n");
		
		/*
		Sort sf1 = new Sort(new SortField[] {
				SortField.FIELD_SCORE,
				new SortField("category", SortField.Type.STRING),
				new SortField("sub_cat", SortField.Type.STRING) });
				*/
		
		Sort sf1 = new Sort(new SortField[] {
				SortField.FIELD_SCORE,
				new SortField("sub_cat", SortField.Type.STRING, true)});
		
		TopFieldCollector collector1 = TopFieldCollector.create(sf1, hitsPerPage, true, true,true, false);
		
		searcher.search(q, collector1);
		ScoreDoc[] hits = collector1.topDocs().scoreDocs;
		
		
		
		System.out.println("Number of Documents found with the search query: " + hits.length);
		for (int k = 0; k < hits.length; k++) {
			int docId = hits[k].doc;
			Document doc = searcher.doc(docId);
			System.out.print("Score = "+hits[k].score);
			System.out.print("  and docId = "+docId+"\n");
		}
		//searcher.close();
		directory.close();

	}
}
