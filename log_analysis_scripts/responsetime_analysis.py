import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.ticker import FuncFormatter

def analyze():
    data = []

    with open("/Users/sriharsha/my-workspace/protobuf/logs/client.log") as f:
        for line in f:
            message = line.strip().split(' - ')[1]
            info, time_in_ms = message.strip().split(':')
            if info.find("Response") != -1:
                time = int(time_in_ms.strip().split('ms')[0])
                info_split = info.strip().split(' ')
                type = info_split[3]
                java_objects = info_split[6]
                data.append({"type": type, "ms": time, "no_of_objects" : java_objects})

    df = pd.DataFrame(data)

    plt.figure(figsize=(10, 6))

    for t in df['type'].unique():
        subset = df[df['type'] == t]
        plt.plot(subset['no_of_objects'], subset['ms'], label=t, marker='o')

    plt.title('Response Time by Number of Objects')
    plt.xlabel('Number of Objects')
    plt.ylabel('Time (ms)')
    plt.legend()
    plt.gca().yaxis.set_major_formatter(FuncFormatter(lambda x, _: f'{int(x)}'))
    plt.grid(True)
    plt.show()

analyze()