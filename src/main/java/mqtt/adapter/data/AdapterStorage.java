package mqtt.adapter.data;

public interface AdapterStorage<T extends Object> {
    void store(String topicName, T data);
    T getLastMessage(String topic);
    boolean contains(String topic);
}
