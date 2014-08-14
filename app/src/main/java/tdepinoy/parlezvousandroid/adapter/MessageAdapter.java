package tdepinoy.parlezvousandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import tdepinoy.parlezvousandroid.R;
import tdepinoy.parlezvousandroid.model.Message;

public class MessageAdapter extends BaseAdapter {

    private List<Message> messages = Collections.EMPTY_LIST;

    private final Context context;

    public MessageAdapter (Context context) {
        this.context = context;
    }

    public void updateMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.list_message, parent, false);
        }

        TextView authorView = (TextView) convertView.findViewById(R.id.message_author);
        TextView contentView = (TextView) convertView.findViewById(R.id.message_content);

        Message message = getItem(position);
        authorView.setText(message.getAuthor());
        contentView.setText(message.getContent());

        return convertView;
    }
}
