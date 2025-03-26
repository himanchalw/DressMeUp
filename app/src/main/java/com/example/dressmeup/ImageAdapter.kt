import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dressmeup.ImageItem
import com.example.dressmeup.R
import com.example.dressmeup.GetImages
import com.example.dressmeup.Post
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ImageAdapter(private val imageList: MutableList<ImageItem>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageItem = imageList[position]

        val bitmap:Bitmap? = decodeBase64ToBitmap(imageItem.imageUrl)

        if(bitmap!=null){
            holder.imageView.setImageBitmap(bitmap)
        }

//        Glide.with(holder.itemView.context)
//            .load(imageItem.imageUrl)
//            .into(holder.imageView)
    }
    // 🔹 Add a function to update the data dynamically
    fun updateData(newItems: List<ImageItem>) {
        imageList.clear()
        imageList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }

    override fun getItemCount(): Int = imageList.size
}
