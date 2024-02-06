# GenoDesigner
GenoDesigner: an open-source software for synthetic genome design.

Genome design is crucial for synthesizing genomes, with larger genomes necessitating a more systematic approach. The design of SynMoss involves numerous modifications to the Physcomitrium patens genome, requiring user-friendly software for generating synthetic sequences. To address this, we have developed GenoDesigner, an online tool that allows researchers to manipulate DNA sequences within genomes or chromosomes. GenoDesigner is adept at handling genome designs up to the gigabase level, providing valuable support for expediting the synthesis of the P. patens genome and advancing related biological research.

The supplement Python and R scripts of GenoDesigner can be found at https://github.com/WenfeiY/P.patens_geno_design.

# Requirements
Linux server with 4+ core, 16 GB+ memory, and 500 GB+ hard drive.

Docker version 20 or higher.

# Installation
```
wget https://github.com/SynMoss/GenoDesigner/releases/download/GenoDesigner-v1.0/GenoDesigner-1.0.tar.gz

tar -zxvf GenoDesigner-1.0.tar.gz

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
