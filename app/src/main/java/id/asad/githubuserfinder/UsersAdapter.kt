package id.asad.githubuserfinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import id.asad.githubuserfinder.model.ItemsItem
import kotlinx.android.synthetic.main.item_row_user.view.*

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.MyViewHolder> () {

    private var listUsers : ArrayList<ItemsItem?>? = ArrayList()

    fun setDataAdapter(list: ArrayList<ItemsItem?>?){
        this.listUsers?.clear()
        this.listUsers?.addAll(list!!)
        notifyDataSetChanged()
    }

    class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvUserName: TextView = view.tv_name_user
        val imgUser: CircleImageView = view.img_user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false))
    }

    override fun getItemCount(): Int {
        return listUsers!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.tvUserName.text = listUsers?.get(position)?.login
       Glide.with(holder.itemView.context).load(listUsers?.get(position)?.avatarUrl).error(R.mipmap.ic_launcher_round).into(holder.imgUser)
    }
}