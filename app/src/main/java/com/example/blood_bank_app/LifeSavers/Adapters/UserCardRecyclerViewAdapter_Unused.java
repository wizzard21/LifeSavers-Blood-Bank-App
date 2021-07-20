package com.example.blood_bank_app.LifeSavers.Adapters;

/*
public class UserCardRecyclerViewAdapter extends RecyclerView.Adapter<UserCardViewHolder> {

    private List<UserEntry> userList;
    private ImageRequester imageRequester;

    UserCardRecyclerViewAdapter(List<UserEntry> userList) {
        this.userList = userList;
        imageRequester = ImageRequester.getInstance();
    }


    @NonNull
    @Override
    public UserCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card_layout, parent, false);
        return new UserCardViewHolder(layoutView);
    }


    @Override
    public void onBindViewHolder (@NonNull UserCardViewHolder holder, int position) {
        if(userList!=null && position<userList.size()) {
            UserEntry user = userList.get(position);
            holder.userName.setText(user.user_name);
            holder.userBloodGrp.setText(user.donor_blood_grp);
            holder.userContact.setText(user.donor_blood_grp);
            imageRequester.setImageFromUrl(holder.userImage, user.url);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


}
*/
