package Utils;

import android.content.Context;
import com.example.myapplication.ml.LiteModelMovenetSingleposeLightningTfliteFloat164;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class PoseRecognition {
    public void load_model(Context context) {
        /**
        try {
            LiteModelMovenetSingleposeLightningTfliteFloat164 model = LiteModelMovenetSingleposeLightningTfliteFloat164.newInstance(context);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 192, 192, 3}, DataType.UINT8);
            int [][][][]mid=new int[1][192][192][3];
            ByteBuffer byteBuffer=ByteBuffer.wrap(mid.toString().getBytes());
            inputFeature0.loadBuffer(byteBuffer);
            // Runs model inference and gets result.
            LiteModelMovenetSingleposeLightningTfliteFloat164.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
         **/
    }
}
