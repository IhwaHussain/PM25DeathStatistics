
# PM25DeathStatistics

[![Java](https://img.shields.io/badge/language-Java-blue.svg)](https://www.java.com/) [![Hadoop](https://img.shields.io/badge/built%20with-Hadoop-yellowgreen.svg)](https://hadoop.apache.org/)

A technical analysis exploring the relationship between ambient PM2.5 air pollution and US death statistics. This project leverages **Hadoop** (MapReduce paradigm) and **Java** to process, join, and analyze large-scale public health and air quality datasets. The codebase is modularized to accommodate flexible data preprocessing, statistical profiling, and complex joins required for environmental data analysis.

https://air-quality-insights.replit.app/
![National PM2.5 Distribution](assets/choropleth-preview.png)
---

## 📊 Project Overview

PM25DeathStatistics examines links between fine particulate matter (PM2.5) and mortality across the United States at varying temporal and spatial scales. By harnessing scalable distributed computation with Hadoop, the project processes public datasets, standardizes health and pollution metrics, and enables reproducible research on environmental impact.

---

## 🛠️ Tech Stack

- **Hadoop** (MapReduce): For distributed, parallel computation on large datasets
- **Java**: Main programming language for MapReduce jobs and data processing logic

---

## 📁 Folder Structure

- **`IHME/`**  
  Contains scripts and Hadoop jobs for loading, cleaning, and preliminary processing of raw Institute for Health Metrics and Evaluation (IHME) death statistics.

- **`IHMEProfile/`**  
  Includes profiling tools and scripts to summarize, validate, and visualize the IHME datasets after initial preprocessing.

- **`PMJoin/`**  
  Implements core MapReduce jobs for joining processed IHME mortality data with PM2.5 air pollution datasets. This is the main analytics entrypoint for correlating pollution to outcomes.

---

## 🚀 How to Run

> **Requirements:**  
> - Java 8+  
> - Hadoop 2.x or later configured and running  
> - Input datasets (see "Data Sources" section below)

1. **Clone the repository**
    ```bash
    git clone https://github.com/IhwaHussain/PM25DeathStatistics.git
    cd PM25DeathStatistics
    ```

2. **Build Java sources (replace with actual build commands if using Maven/Gradle)**
    ```bash
    javac -cp "<your-hadoop-classpath>" IHME/*.java IHMEProfile/*.java PMJoin/*.java
    ```

3. **Package Hadoop jobs (optional if using .jar)**
    ```bash
    jar -cvf PM25DeathStatistics.jar IHME/*.class IHMEProfile/*.class PMJoin/*.class
    ```

4. **Run Hadoop jobs**  
   *The following is a generic command; replace `<JobClass>`, `<input>`, and `<output>` with actual values as applicable.*
    ```bash
    hadoop jar PM25DeathStatistics.jar <JobClass> <input_path> <output_path>
    ```

    - For example, to run the join job:
        ```bash
        hadoop jar PM25DeathStatistics.jar PMJoin.PMDeathJoinJob <input_data> <output_results>
        ```

---

## 📚 Data Sources

- **IHME Death Statistics:** [PLACEHOLDER: Add data source link, e.g., Global Burden of Disease Study or IHME U.S. Mortality Data]
- **PM2.5 Air Quality Data:** [PLACEHOLDER: Add link/source to EPA, NASA, or relevant PM2.5 measurements]
- **Additional Sources:**  
  - [PLACEHOLDER: Add any other relevant datasets]

---

## 📈 Results & Findings

[PLACEHOLDER: Summarize major research results, statistical findings, and/or data visualizations.]

---

## 🤝 Contributing

Pull requests and issue reports are welcome! For significant changes, please open an issue first to discuss your ideas.

---

## 📄 License

[PLACEHOLDER: Specify your project's license here if applicable.]
````
