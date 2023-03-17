import cv2
import numpy as np
import matplotlib.pyplot as plt
import io
from PIL import Image
import base64

def main(x , y):
    fig = plt.figure()

    x = x.split(",")
    y = y.split(",")

    x_data = []
    y_data = []

    for i in x:
        x_data.append(int(i))

    for i in y:
        y_data.append(int(i))

    ay = fig.add_subplot(1,1,1)
    ay.plot(x_data, y_data)

    fig.canvas.draw()  #now we will use this canvas to convert data to numpy array

    img = np.fromstring(fig.canvas.tostring_rgb(), dtype = np.uint8, sep='')
    img = img.reshape(fig.canvas.get_width_height()[::-1] + (3,)) #reshape data
    img = cv2.cvtColor(img, cv2.COLOR_RGB2BGR)

    pil_im = Image.fromarray(img)
    buff = io.BytesIO()
    pil_im.save(buff, format="PNG")
    img_str = base64.b64encode(buff.getvalue())
    return ""+str(img_str, 'utf-8')

