# GenoDesigner
GenoDesigner: an open-source software for synthetic genome design.

Genome design stands as the fundamental pillar of genome synthesis. While earlier work on virus and bacteria genome synthesis adhered to a few simple design rules. recent progress in larger genome synthesis demands more systematic modifications to the original genome, leading to a comprehensive design process. As genome size increases, its design complexity grows substantially. The design of SynMoss includes numerous modifications in _Physcomitrium patens_ genome, thus requiring fast and easy-to-use software to generate the synthetic sequences. Consequently, many design-related tasks were manually and painstakingly executed by front-line scientists, such as the inspection of a missing fragment in the synthetic chromosome. In pursuit of this objective, we have developed “GenoDesigner”, an intuitive online genome design tool. This tool empowers researchers to manipulate target DNA sequences in each genome or chromosome. With a user-friendly interface, GenoDesigner is adept at handling genome designs up to the gigabase level. We anticipate that the principles, experiences, and the GenoDesigner tool outlined here will serve as invaluable resources, not only expediting the synthesis of the P. patens genome but also facilitating related biological research. 

# Requirements
Linux server with 4+ core, 16 GB+ memory, and 500 GB+ hard drive.

Docker version 20 or higher.

# Installation
```
wget https://github.com/SynMoss/GenoDesigner/archive/refs/tags/GenoDesigner-v1.0.tar.gz

tar -zxvf GenoDesigner-v1.0.tar.gz

cd GenoDesigner-1.0

sudo bash init_and_start.sh
```
Visit http://<IP_of_server>:3009/client/en/index.html through browser.

**Note:** Please make sure that port 3009 is available.

# Instruction
The PDF files in the **instruction** directory contain detailed information on GenoDesigner.

**About-GenoDesigner.pdf** contains the basic information and description of GenoDesigner.

**Example-GenoDesigner.pdf** contains the workflow of designing SynMoss Chr16L using GenoDesigner.

**Help-GenoDesigner.pdf** contains the instructions for every function in GenoDesigner.

# Example
Files in the **example** directory are required for designing Chr16L of SynMoss.

**Pp.Chr16L.gb** contains sequence and annotations of the left arm of _P. patens_ Chr16.

**Pp.Chr16L_PCRmark.txt** contains information on PCRmarks designed for Chr16L.

**SynMoss.Chr16L.gbff** is the example output file of the designed SynMoss Chr16L.

# Cite
For any citation, please refer to: 
Wenfei Yu, Shuo Zhang, Shijun Zhao, Lian-ge Chen, Jianbin Yan, Qiao Zhao, Beixin Mo, Ying Wang, Yuling Jiao, Yixing Ma, Xiaoluo Huang, Wenfeng Qian, Junbiao Dai. “Design a synthetic Moss genome using GenoDesigner”. In submission. 
