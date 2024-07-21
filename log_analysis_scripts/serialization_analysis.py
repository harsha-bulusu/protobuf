import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator

def analyze():
  data = []

  with open("/Users/sriharsha/my-workspace/protobuf/logs/server.log") as f:
    for line in f:
      message = line.strip().split(" - ")[1]
      if message.find("serializing") != -1:
        info, time_in_ms = message.split(":")
        info_split = info.split(" ")
        java_objects = info_split[4]
        type = info_split[-2]
        time = int(time_in_ms.split("ms")[0])
        data.append({"type": type, "ms": time, "no_of_objects" : java_objects})

    df = pd.DataFrame(data)

    plt.figure(figsize=(10, 6))

    for t in df['type'].unique():
        subset = df[df['type'] == t]
        plt.plot(subset['no_of_objects'], subset['ms'], label=t, marker='o')

    plt.title('Serialization Time by Number of Objects')
    plt.xlabel('Number of Objects')
    plt.ylabel('Time (ms)')
    plt.legend()
    # Set y-axis limits and tick intervals
    plt.gca().yaxis.set_major_locator(MaxNLocator(integer=True))
    
    # Customize y-axis ticks and limits
    plt.ylim(0, df['ms'].max() + 10)
    plt.yticks(range(0, df['ms'].max() + 10, 10))
    plt.grid(True)
      