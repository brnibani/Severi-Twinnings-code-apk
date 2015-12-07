package com.app.gabriele.severitwinnings.listeners;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.app.gabriele.severitwinnings.data.RssAtomItem;
import com.app.gabriele.severitwinnings.DetailsActivity;

public class ListListener implements OnItemClickListener {

    // List item's reference
    List<RssAtomItem> listItems;
    // Calling activity reference
    Activity activity;

    public ListListener(List<RssAtomItem> aListItems, Activity anActivity) {
        listItems = aListItems;
        activity  = anActivity;
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Intent i = new Intent(activity, DetailsActivity.class);
        i.setData(Uri.parse(listItems.get(pos).getContent()));
        i.putExtra("link", listItems.get(pos).getLink());
        i.putExtra("title", listItems.get(pos).getTitle());
        i.putExtra("content:encoded", listItems.get(pos).getContent());
        i.putExtra("category", listItems.get(pos).getCategory());
        i.putExtra("wfw:commentRss", listItems.get(pos).getLinkComment());
        i.putExtra("slash:comments", listItems.get(pos).getNumComments());
        activity.startActivity(i);
    }

}
