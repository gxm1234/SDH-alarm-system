
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Apriori {
	//private File outputFile;
	//private File inputFile;
	private String[] input;
	//最小支持度，用0-1的比例来表示.
	private double mimumSupportValue;
	//输入文件的记录
	private List<Set<String>> records;
	private List<List<ItemSet>> resultList;
	private int itemCount;

	public Apriori(String[] input,double mimumSupportVaule){
		//this.inputFile = inputFile;
		//this.outputFile = outputFile;
		this.input=input;
		this.mimumSupportValue = mimumSupportVaule;
		this.itemCount = 0;
		this.records = new ArrayList<Set<String>>();
		this.resultList = new ArrayList<List<ItemSet>>();
	}
	
	public ArrayList mine(){
		init();
		if(mimumSupportValue <= (1.0/itemCount)){
			System.err.println("supportValue to small!");
			System.exit(0);
		}
		//第一次初始化
		List<ItemSet> kRoundResult = new ArrayList<Apriori.ItemSet>();
		for(Set<String> record:records){
			for(String recordItem:record){
				ItemSet itemSet = new ItemSet();
				itemSet.addItem(recordItem);
				if(!kRoundResult.contains(itemSet)){
					kRoundResult.add(itemSet);
					
				}
			}
		}
		
		count(kRoundResult);
		prun(kRoundResult);
		
		List<ItemSet> temp = new ArrayList<Apriori.ItemSet>();
		temp.addAll(kRoundResult);
		//迭代
		while(kRoundResult.size() > 0){
			resultList.add(kRoundResult);
			kRoundResult = new ArrayList<Apriori.ItemSet>();
			Collections.sort(temp);
			//找到可以合并，生成Ck+1
			for(int i=0;i<temp.size()-1;++i){
				ItemSet firtSet = temp.get(i);
				for(int j=i+1;j<temp.size();++j){
					ItemSet secondSet = temp.get(j);
					ItemSet mergeItemSet = firtSet.merge(secondSet);
					if(mergeItemSet != null){
						if(!kRoundResult.contains(mergeItemSet)){
							kRoundResult.add(mergeItemSet);
						}
					}
				}
			}
			count(kRoundResult);
			prun(kRoundResult);
			temp.clear();
			temp.addAll(kRoundResult);
		}
		
		ArrayList<String[]> out=output();
		return out;
	}
	
	//输出结果到指定文件中
	private ArrayList output(){
		
		
		ArrayList<String[]> output = new ArrayList<String[]>();
		
		int j=0;
		for(int i=0;i<resultList.size();i++){
			
			for(ItemSet itemSet:resultList.get(i)){
				TreeSet<String> item = (TreeSet<String>) itemSet.itemSet;
				ArrayList<String> result = new ArrayList<String>();
				int mapsize = item.size();

				System.out.println("itemsize:"+mapsize);
				
				if(mapsize==2)
				{
					String[] a = new String[2];
					result.addAll(item);
					a[0]=result.get(0);
					a[1]=result.get(1);
					output.add(a);
				}
				else if(mapsize==3)
				{
					result.addAll(item);
					String[] a = new String[3];
					a[0]=result.get(0);
					a[1]=result.get(1);
					a[2]=result.get(2);
					output.add(a);
				}
			}

		}
		
		return output;
		
	}
	
	
	//计数
	private void count(List<ItemSet> setList){
		for(Set<String> record:records){
			for(ItemSet itemSet:setList){
				if(record.containsAll(itemSet.itemSet)){
					itemSet.incSupport();
				}
			}
		}
	}
	
	//剪枝
	private void prun(List<ItemSet> setList){
		for(Iterator<ItemSet> iter = setList.iterator(); iter.hasNext();){
			ItemSet set = iter.next();
			if(set.supportValue/(itemCount+0.0) <mimumSupportValue){
				iter.remove();
			}
		}
	}
	
	//从输入文件中读取记录，假设记录之间由空格隔开
	private void init(){
		
		for(int i=0;i<input.length;i++)
		{
			String[] data = input[i].split(" ");
			Set<String> record = new TreeSet<String>();
			for(String itemData:data){
				record.add(itemData);
			}
			itemCount ++;
			records.add(record);
		}
		
		
	}

	public static class ItemSet implements Comparable<ItemSet>{
		Set<String> itemSet;
		int supportValue;
		
		ItemSet(){
			itemSet = new TreeSet<String>();
			this.supportValue = 0;
		}
		
		public void addItem(String item){
			itemSet.add(item);
		}
		
		//检查是否可以合并
		public ItemSet merge(ItemSet other){
			if(other.itemSet.size() != itemSet.size()) return null;
			ItemSet newItem = new ItemSet();
			Iterator<String> selfIter = itemSet.iterator();
			Iterator<String> otherIter = other.itemSet.iterator();
			int i =0;
			while(i < itemSet.size()-1){
				String selfStr = selfIter.next();
				String otherStr = otherIter.next();
				if(!selfStr.equals(otherStr)) return null;
				i++;
				newItem.itemSet.add(selfStr);
			}
			String selfLastStr = selfIter.next();
			String otherLastStr = otherIter.next();
			if(selfLastStr.compareTo(otherLastStr) > 0) return null;
			newItem.addItem(selfLastStr);
			newItem.addItem(otherLastStr);
			return newItem;
		}
		
		public String toString(){
			String toStr = "";
			for(String str:itemSet){
				toStr += str;
				toStr += " ";
			}
			toStr += supportValue;
			return toStr;
		}
		
		public boolean equals(Object o){
			if(o == this) return true;
			if(!(o instanceof ItemSet)) return false;
			ItemSet other = (ItemSet)o;
			return other.itemSet.equals(itemSet);
		}
		
		public void incSupport(){
			supportValue ++;
		}
		
		public int hashCode(){
			return itemSet.hashCode();
		}

		//实际按字典序排序
		@Override
		public int compareTo(ItemSet o) {
			if(this.equals(o)) return 0;
			Iterator<String> firstIter = itemSet.iterator();
			Iterator<String> secondIter = o.itemSet.iterator();
			while(firstIter.hasNext() && secondIter.hasNext()){
				String firstStr = firstIter.next();
				String secondStr = secondIter.next();
				int code = firstStr.compareTo(secondStr);
				if(code < 0) return -1;
				else if (code > 0) return 1;
			}
			if(firstIter.hasNext()){
				return 1;
			}else{
				return -1;
			}
		}
	}
	

}
