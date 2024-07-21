import serialization_analysis
import responsetime_analysis
import serializationsize_analysis
import deserialization_analysis

import matplotlib.pyplot as plt


serializationsize_analysis.analyze()
serialization_analysis.analyze()
deserialization_analysis.analyze()
responsetime_analysis.analyze()

plt.show()
