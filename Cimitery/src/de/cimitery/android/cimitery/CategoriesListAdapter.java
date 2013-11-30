package de.cimitery.android.cimitery;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class CategoriesListAdapter extends BaseExpandableListAdapter {

	private static final class ViewHolder {
		TextView textLabel;
	}

	private final List<CategoriesItem> itemList;
	private final LayoutInflater inflater;

	public CategoriesListAdapter(Context context, List<CategoriesItem> itemList) {
		this.inflater = LayoutInflater.from(context);
		this.itemList = itemList;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return itemList.get(groupPosition).getChildItemList()
				.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, final ViewGroup parent) {
		View resultView = convertView;
		ViewHolder holder;

		if (resultView == null) {

			resultView = inflater.inflate(android.R.layout.expandable_list_content, null);
															
			holder = new ViewHolder();
			holder.textLabel = (TextView) resultView
					.findViewById(android.R.id.title); // TODO change view id
			resultView.setTag(holder);
		} else {
			holder = (ViewHolder) resultView.getTag();
		}

		final Category item = (Category) getChild(groupPosition, childPosition);

		holder.textLabel.setText(item.toString());

		return resultView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return itemList.get(groupPosition).getChildItemList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return itemList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return itemList.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View theConvertView, ViewGroup parent) {
        View resultView = theConvertView;
        ViewHolder holder;

        if (resultView == null) {
                resultView = inflater.inflate(android.R.layout.expandable_list_content, null); 
                holder = new ViewHolder();
                holder.textLabel = (TextView) resultView.findViewById(android.R.id.title);
                resultView.setTag(holder);
        } else {
                holder = (ViewHolder) resultView.getTag();
        }

        final CategoriesItem item = (CategoriesItem) getGroup(groupPosition);

        holder.textLabel.setText(item.toString());

        return resultView;
}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
