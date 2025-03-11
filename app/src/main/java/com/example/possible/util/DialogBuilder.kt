import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.possible.R

object DialogBuilder {
    fun showAlertDialog(
        context: Context,
        message: String,
        title: String,
        positiveButton: String,
        negativeButton: String,
        onConfirm: () -> Unit,
        onCancel: () -> Unit,
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
        builder.setCancelable(true)
        builder.setTitle(title)

        builder.setPositiveButton(positiveButton) { dialog, _ ->
            onConfirm()
            dialog.dismiss()
        }
        builder.setNegativeButton(negativeButton) { dialog, _ ->
            onCancel()
            dialog.dismiss()

        }
        val dialog = builder.create()

        dialog.show()

    }

    fun showErrorDialog(
        context: Context,
        meesage:String,
        desision:String
    ){
        val errorDialog = Dialog(context)
        errorDialog.setContentView(R.layout.error_dialog)
        errorDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        errorDialog.setCancelable(false)
        val btn = errorDialog.findViewById<AppCompatButton>(R.id.dialogTryAgainBtn)
        val message = errorDialog.findViewById<TextView>(R.id.message)
        message.text=meesage
        btn.text=desision
        btn.setOnClickListener {
            errorDialog.dismiss()
        }
        errorDialog.show()
    }
    fun showSuccessDialog(
        context: Context,
        meesage:String,
        desision:String,
        onConfirm: () -> Unit
        ) {
        val successDialog = Dialog(context)
        successDialog.setContentView(R.layout.success_dialog)
        successDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        successDialog.setCancelable(false)
        val btn = successDialog.findViewById<TextView>(R.id.dialogNext)
        val message = successDialog.findViewById<TextView>(R.id.message)
        message.text = meesage
        btn.text = desision
        btn.setOnClickListener {
            onConfirm()
            successDialog.dismiss()
        }
        successDialog.show()
    }
}