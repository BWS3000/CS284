package cs284;

public interface Map<K, V> extends Iterable<Entry<K, V>> {
  public int size();
  
  public int capacity();
  
  public int rehashes();
  
  public int[] bucketSizes();
  
  public V get(K key);
  
  public boolean containsKey(K key);
  
  public V put(K key, V val);
  
  public V remove(K key);
}