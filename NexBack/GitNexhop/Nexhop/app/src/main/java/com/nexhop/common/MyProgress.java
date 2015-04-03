package com.nexhop.common;


import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

import com.nexhop.R;

public class MyProgress extends Dialog 
{
	static MyProgress dialog ;

   public static MyProgress show(Context context, CharSequence title, CharSequence message) 
   {
       return show(context, title, message, false);
   }

   public static MyProgress show(Context context, CharSequence title, CharSequence message, boolean indeterminate) 
   {
       return show(context, title, message, indeterminate, false, null);
   }

   public static MyProgress show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) 
   {
       return show(context, title, message, indeterminate, cancelable, null);
   }

   public static MyProgress show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) 
   {
        dialog = new MyProgress(context);
        dialog.setTitle(title);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
       
        /* The next line will add the ProgressBar to the dialog. */
        dialog.addContentView(new ProgressBar(context), new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        dialog.show();

        return dialog;
   }
   public static boolean isShowingProgress()
   {
	   if(dialog==null){
		   return false;
	   }
	   return dialog.isShowing();
   }
   
   public static void CancelDialog()
   {
	   try{
		   if(dialog != null && dialog.isShowing())
		   {
			   dialog.dismiss();
		   }
	   }
	   catch(IllegalArgumentException e){
		   e.printStackTrace();
	   }
	   catch(Exception e){
		   e.printStackTrace();
	   }
   }

   public MyProgress(Context context)
   {
       super(context, R.style.NewDialog);
   }
}
