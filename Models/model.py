import tensorflow
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, Dropout
import sys
import argparse
import os
import cv2

parser = argparse.ArgumentParser(description="Required: input_size[x,y], num_class=int || Optional: JSON file that contains architecture")
parser.add_argument("--input_size", nargs=2, help="resolution of input images example --input_size 1920 1080")
parser.add_argument("--num_class", help="number of classes the model will output.")
parser.add_argument("--dir", help="path to directory that contains images. Each class should have its own sub folder in the directory supplied")
parser.add_argument("--model",help="path to json file that contains model architecture [optional]")

def model(json):
    if json is not None:
        print("create model based on json")
    model = Sequential([
        Conv2D(32, (3,3), activation='relu', input_shape=(args.input_size[0], args.input_size[1], 3)),
        MaxPooling2D(pool_size=(2,2)),
        Dropout(0.15),
        Flatten(),
        Dense(args.num_class, activation="softmax")
    ])
    return model

def loadMainDirectory(path):
    if os.path.exists(path):
        directories=filter(os.path.isdir, os.listdir(path))
        labels=[]
        images=[]
        label_id=0
        for directory in directories:
            results=loadDirectory(path+'/'+directory)
            images=images + results
            labels = labels +[label_id]*len(results)
            label_id+=1
        return images,labels, directory
    return None



def loadDirectory(path):
    if os.path.exists(path):
        files=filter(lambda x: x.endswith('.png'),os.listdir(path))
        images=[]
        for file in files:
            images.append(cv2.imread(file))
        return images
    return None        


def main():
    args=parser.parse_args()
    if(args.dir is None or args.num_class is None or args.input_size is None):
        exit(-1)
    model=model(args.model)
    images,labels, directory=loadMainDirectory(args.dir)
    model.compile(
        optimizer=keras.optimizers.SGD(learning_rate=0.1, momentum=0.9),
        loss='sparse_categorical_crossentropy',
        metrics=['sparse_categorical_accuracy'])

    model.fit((images,labels), epochs=5)
    model.evalute((images,labels))
    model.save("classes-"+str(args.num_class)+"dir-"+directory+".h5")
    return

if __name__ == "__main__":
    main()