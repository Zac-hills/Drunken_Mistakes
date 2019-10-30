package Vision;
//Tensorflow java-api
//https://www.tensorflow.org/api_docs/java/reference/org/tensorflow/package-summary
import java.util.Timer;
import org.tensorflow.*;

public class TensorModel {
    interface callBack
    {
        void ReceiveModelOutput(float[] a_Output, int a_SizeOfOutput);
    }
    TensorModel(String a_PathToModel)
    {
        m_PathToModel = a_PathToModel;
        model = SavedModelBundle.load(a_PathToModel);
    }
    void Execute(float[] a_Data)
    {

    }
    String m_PathToModel;
    int m_NumOfOutputs=0;
    int[] m_InputSize;
    Thread m_ComputeThread;
    Timer m_Timer;
    SavedModelBundle model;
}
