package mqtt.adapter.data;

public interface AdapterStorage {
    void store(String topicName, String data);
    String getLastMessage(String topic);
}
