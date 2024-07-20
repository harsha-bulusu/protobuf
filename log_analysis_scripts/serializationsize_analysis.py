import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.ticker import MaxNLocator

def analyze():
    data = []

    with open("/Users/sriharsha/my-workspace/protobuf/logs/client.log") as f:
        for line in f:
            message = line.strip().split('-')[1]
            info, size_in_bytes = message.strip().split(':')

            if info.startswith('Size'):
                size = int(size_in_bytes.strip().split('bytes')[0])//1024
                info_split = info.strip().split(' ')
                type = info_split[2]
                java_objects = info_split[6]
                data.append({"type": type, "size": size, "no_of_objects" : java_objects})

    df = pd.DataFrame(data)
    
    plt.figure(figsize=(10, 6))

    for t in df['type'].unique():
        subset = df[df['type'] == t]
        plt.plot(subset['no_of_objects'], subset['size'], label=t, marker='o')

    plt.title('Deserialization Time by Number of Objects')
    plt.xlabel('Number of Objects')
    plt.ylabel('Size (bytes)')
    plt.legend()
    # Set y-axis limits and tick intervals
    plt.gca().yaxis.set_major_locator(MaxNLocator(integer=True))
    
    # Customize y-axis ticks and limits
    plt.ylim(0, df['size'].max() + 10)
    plt.yticks(range(0, df['size'].max() + 10, 4000))
    plt.grid(True)
    plt.show()

analyze()