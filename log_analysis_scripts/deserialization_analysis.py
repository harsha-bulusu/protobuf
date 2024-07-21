import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator

def analyze():
    data = []

    with open("/Users/sriharsha/my-workspace/protobuf/logs/client.log") as f:
        for line in f:
            message = line.strip().split('-')[1]
            info, time_in_ms = message.strip().split(':')
            if info.find("Deserialization") != -1:
                time = int(time_in_ms.strip().split('ms')[0])
                info_split = info.split(" ")
                type = info.strip().split(' ')[5]
                java_objects = info_split[7]
                data.append({"type": type, "ms": time, "no_of_objects" : java_objects})


    df = pd.DataFrame(data)
    
    plt.figure(figsize=(10, 6))

    for t in df['type'].unique():
        subset = df[df['type'] == t]
        plt.plot(subset['no_of_objects'], subset['ms'], label=t, marker='o')

    plt.title('Deserialization Time by Number of Objects')
    plt.xlabel('Number of Objects')
    plt.ylabel('Time (ms)')
    plt.legend()
    # Set y-axis limits and tick intervals
    plt.gca().yaxis.set_major_locator(MaxNLocator(integer=True))
    
    # Customize y-axis ticks and limits
    plt.ylim(0, df['ms'].max() + 10)
    plt.yticks(range(0, df['ms'].max() + 10, 20))
    plt.grid(True)
