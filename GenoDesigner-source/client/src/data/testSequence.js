export const mockSequence = `LOCUS       NC_001224.1            85779 bp    DNA     linear   SYN 29-NOV-2022
DEFINITION  NC_001224.1 Saccharomyces cerevisiae S288c mitochondrion, complete genome.
ACCESSION  NC_001224
VERSION  NC_001224.1
KEYWORDS    .
SOURCE      .
  ORGANISM  .
            .
FEATURES             Location/Qualifiers
     source          1..85779
                     /label="Untitled Feature"
                     /ID="NC_001224.1:1..85779"
                     /Dbxref="taxon:559292"
                     /Is_circular="true"
                     /Name="MT"
                     /gbkey="Src"
                     /genome="mitochondrion"
                     /mol_type="genomic DNA"
                     /strain="S288C"
                     /substrain="FY1679"
                     /source="RefSeq1"
     gene            731..802
                     /label="tP(UGG)Q"
                     /ID="gene-tP(UGG)Q"
                     /Dbxref="GeneID:854578"
                     /Dbxref="SGD:S000007328"
                     /Name="tP(UGG)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tP(UGG)Q"
                     /source="RefSeq1"
     tRNA            731..802
                     /label="tP(UGG)Q"
                     /ID="rna-tP(UGG)Q"
                     /Parent="gene-tP(UGG)Q"
                     /Dbxref="GeneID:854578"
                     /Dbxref="SGD:S000007328"
                     /Note="Mitochondrial proline tRNA (tRNA-Pro)"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tP(UGG)Q"
                     /product="tRNA-Pro"
                     /source="RefSeq1"
     exon            731..802
                     /label="tP(UGG)Q"
                     /ID="exon-tP(UGG)Q-1"
                     /Parent="rna-tP(UGG)Q"
                     /Dbxref="GeneID:854578"
                     /Dbxref="SGD:S000007328"
                     /Note="Mitochondrial proline tRNA (tRNA-Pro)"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tP(UGG)Q"
                     /product="tRNA-Pro"
                     /source="RefSeq1"
     rep_origin      complement(4012..4312)
                     /label="Untitled Feature"
                     /ID="id-NC_001224.1:4012..4312"
                     /Dbxref="SGD:S000029667"
                     /Note="ORI1; Mitochondrial origin of replication"
                     /gbkey="rep_origin"
                     /source="RefSeq1"
     gene            6546..8194
                     /label="15S_RRNA"
                     /ID="gene-Q0020"
                     /Dbxref="GeneID:9164990"
                     /Dbxref="SGD:S000007287"
                     /Name="15S_RRNA"
                     /gbkey="Gene"
                     /gene_biotype="rRNA"
                     /locus_tag="Q0020"
                     /source="RefSeq1"
     rRNA            6546..8194
                     /label="15S_RRNA"
                     /ID="rna-Q0020"
                     /Parent="gene-Q0020"
                     /Dbxref="GeneID:9164990"
                     /Dbxref="SGD:S000007287"
                     /Note="Ribosomal RNA of the small mitochondrial ribosomalsubunit; MSU1 allele suppresses ochre stop mutations inmitochondrial protein-coding genes"
                     /experiment="EXISTENCE:curator inference:GO:0003735structural constituent of ribosome [PMID:6262728]"
                     /experiment="EXISTENCE:curator inference:GO:0032543mitochondrial translation [PMID:6262728]"
                     /experiment="EXISTENCE:direct assay:GO:0005763mitochondrial small ribosomal subunit [PMID:6262728]"
                     /gbkey="rRNA"
                     /locus_tag="Q0020"
                     /product="15S ribosomal RNA"
                     /source="RefSeq1"
     exon            6546..8194
                     /label="15S_RRNA"
                     /ID="exon-Q0020-1"
                     /Parent="rna-Q0020"
                     /Dbxref="GeneID:9164990"
                     /Dbxref="SGD:S000007287"
                     /Note="Ribosomal RNA of the small mitochondrial ribosomalsubunit; MSU1 allele suppresses ochre stop mutations inmitochondrial protein-coding genes"
                     /experiment="EXISTENCE:curator inference:GO:0003735structural constituent of ribosome [PMID:6262728]"
                     /experiment="EXISTENCE:curator inference:GO:0032543mitochondrial translation [PMID:6262728]"
                     /experiment="EXISTENCE:direct assay:GO:0005763mitochondrial small ribosomal subunit [PMID:6262728]"
                     /gbkey="rRNA"
                     /locus_tag="Q0020"
                     /product="15S ribosomal RNA"
                     /source="RefSeq1"
     gene            9374..9447
                     /label="tW(UCA)Q"
                     /ID="gene-tW(UCA)Q"
                     /Dbxref="GeneID:854581"
                     /Dbxref="SGD:S000007337"
                     /Name="tW(UCA)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tW(UCA)Q"
                     /source="RefSeq1"
     tRNA            9374..9447
                     /label="tW(UCA)Q"
                     /ID="rna-tW(UCA)Q"
                     /Parent="gene-tW(UCA)Q"
                     /Dbxref="GeneID:854581"
                     /Dbxref="SGD:S000007337"
                     /Note="Mitochondrial tRNA, annotated as tryptophanyl tRNA(tRNA-Trp); predicted by tRNAscan-SE analysis to havespecificity for selenocysteine"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tW(UCA)Q"
                     /product="tRNA-Trp"
                     /source="RefSeq1"
     exon            9374..9447
                     /label="tW(UCA)Q"
                     /ID="exon-tW(UCA)Q-1"
                     /Parent="rna-tW(UCA)Q"
                     /Dbxref="GeneID:854581"
                     /Dbxref="SGD:S000007337"
                     /Note="Mitochondrial tRNA, annotated as tryptophanyl tRNA(tRNA-Trp); predicted by tRNAscan-SE analysis to havespecificity for selenocysteine"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tW(UCA)Q"
                     /product="tRNA-Trp"
                     /source="RefSeq1"
     rep_origin      complement(12510..12780)
                     /label="Untitled Feature"
                     /ID="id-NC_001224.1:12510..12780"
                     /Dbxref="SGD:S000029331"
                     /Note="ORI8; Mitochondrial origin of replication"
                     /gbkey="rep_origin"
                     /source="RefSeq1"
     gene            13818..26701
                     /label="COX1"
                     /ID="gene-Q0045"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /Name="COX1"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /gene_synonym="OXI3"
                     /locus_tag="Q0045"
                     /source="RefSeq1"
     mRNA            13818..26701
                     /label="COX1"
                     /ID="rna-Q0045"
                     /Parent="gene-Q0045"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /gbkey="mRNA"
                     /locus_tag="Q0045"
                     /source="RefSeq2"
     exon            13818..13986
                     /label="COX1"
                     /ID="exon-Q0045-1"
                     /Parent="rna-Q0045"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /gbkey="mRNA"
                     /locus_tag="Q0045"
                     /source="RefSeq2"
     exon            16435..16470
                     /label="COX1"
                     /ID="exon-Q0045-2"
                     /Parent="rna-Q0045"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /gbkey="mRNA"
                     /locus_tag="Q0045"
                     /source="RefSeq2"
     exon            18954..18991
                     /label="COX1"
                     /ID="exon-Q0045-3"
                     /Parent="rna-Q0045"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /gbkey="mRNA"
                     /locus_tag="Q0045"
                     /source="RefSeq2"
     exon            20508..20984
                     /label="COX1"
                     /ID="exon-Q0045-4"
                     /Parent="rna-Q0045"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /gbkey="mRNA"
                     /locus_tag="Q0045"
                     /source="RefSeq2"
     exon            21995..22246
                     /label="COX1"
                     /ID="exon-Q0045-5"
                     /Parent="rna-Q0045"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /gbkey="mRNA"
                     /locus_tag="Q0045"
                     /source="RefSeq2"
     exon            23612..23746
                     /label="COX1"
                     /ID="exon-Q0045-6"
                     /Parent="rna-Q0045"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /gbkey="mRNA"
                     /locus_tag="Q0045"
                     /source="RefSeq2"
     exon            25318..25342
                     /label="COX1"
                     /ID="exon-Q0045-7"
                     /Parent="rna-Q0045"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /gbkey="mRNA"
                     /locus_tag="Q0045"
                     /source="RefSeq2"
     exon            26229..26701
                     /label="COX1"
                     /ID="exon-Q0045-8"
                     /Parent="rna-Q0045"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /gbkey="mRNA"
                     /locus_tag="Q0045"
                     /source="RefSeq2"
     CDS             13818..13986
                     /label="COX1"
                     /ID="cds-NP_009305.1"
                     /Parent="rna-Q0045"
                     /Dbxref="Genbank:NP_009305.1"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /Name="NP_009305.1"
                     /Note="Subunit I of cytochrome c oxidase (Complex IV);Complex IV is the terminal member of the mitochondrialinner membrane electron transport chain; one of threemitochondrially-encoded subunits; number of introns indifferent strains varies from 2 to 8, with most strainshaving 4-6 introns"
                     /experiment="EXISTENCE:direct assay:GO:0004129 cytochrome-coxidase activity [PMID:1331058|PMID:7851399]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005751mitochondrial respiratory chain complex IV [PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0006123mitochondrial electron transport, cytochrome c to oxygen[PMID:1331058]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:9724417]"
                     /gbkey="CDS"
                     /locus_tag="Q0045"
                     /product="cytochrome c oxidase subunit 1"
                     /protein_id="NP_009305.1"
                     /transl_table="3"
                     /source="RefSeq2"
                     /phase="0"
     CDS             16435..16470
                     /label="COX1"
                     /ID="cds-NP_009305.1"
                     /Parent="rna-Q0045"
                     /Dbxref="Genbank:NP_009305.1"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /Name="NP_009305.1"
                     /Note="Subunit I of cytochrome c oxidase (Complex IV);Complex IV is the terminal member of the mitochondrialinner membrane electron transport chain; one of threemitochondrially-encoded subunits; number of introns indifferent strains varies from 2 to 8, with most strainshaving 4-6 introns"
                     /experiment="EXISTENCE:direct assay:GO:0004129 cytochrome-coxidase activity [PMID:1331058|PMID:7851399]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005751mitochondrial respiratory chain complex IV [PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0006123mitochondrial electron transport, cytochrome c to oxygen[PMID:1331058]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:9724417]"
                     /gbkey="CDS"
                     /locus_tag="Q0045"
                     /product="cytochrome c oxidase subunit 1"
                     /protein_id="NP_009305.1"
                     /transl_table="3"
                     /source="RefSeq2"
                     /phase="2"
     CDS             18954..18991
                     /label="COX1"
                     /ID="cds-NP_009305.1"
                     /Parent="rna-Q0045"
                     /Dbxref="Genbank:NP_009305.1"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /Name="NP_009305.1"
                     /Note="Subunit I of cytochrome c oxidase (Complex IV);Complex IV is the terminal member of the mitochondrialinner membrane electron transport chain; one of threemitochondrially-encoded subunits; number of introns indifferent strains varies from 2 to 8, with most strainshaving 4-6 introns"
                     /experiment="EXISTENCE:direct assay:GO:0004129 cytochrome-coxidase activity [PMID:1331058|PMID:7851399]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005751mitochondrial respiratory chain complex IV [PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0006123mitochondrial electron transport, cytochrome c to oxygen[PMID:1331058]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:9724417]"
                     /gbkey="CDS"
                     /locus_tag="Q0045"
                     /product="cytochrome c oxidase subunit 1"
                     /protein_id="NP_009305.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     CDS             20508..20984
                     /label="COX1"
                     /ID="cds-NP_009305.1"
                     /Parent="rna-Q0045"
                     /Dbxref="Genbank:NP_009305.1"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /Name="NP_009305.1"
                     /Note="Subunit I of cytochrome c oxidase (Complex IV);Complex IV is the terminal member of the mitochondrialinner membrane electron transport chain; one of threemitochondrially-encoded subunits; number of introns indifferent strains varies from 2 to 8, with most strainshaving 4-6 introns"
                     /experiment="EXISTENCE:direct assay:GO:0004129 cytochrome-coxidase activity [PMID:1331058|PMID:7851399]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005751mitochondrial respiratory chain complex IV [PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0006123mitochondrial electron transport, cytochrome c to oxygen[PMID:1331058]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:9724417]"
                     /gbkey="CDS"
                     /locus_tag="Q0045"
                     /product="cytochrome c oxidase subunit 1"
                     /protein_id="NP_009305.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             21995..22246
                     /label="COX1"
                     /ID="cds-NP_009305.1"
                     /Parent="rna-Q0045"
                     /Dbxref="Genbank:NP_009305.1"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /Name="NP_009305.1"
                     /Note="Subunit I of cytochrome c oxidase (Complex IV);Complex IV is the terminal member of the mitochondrialinner membrane electron transport chain; one of threemitochondrially-encoded subunits; number of introns indifferent strains varies from 2 to 8, with most strainshaving 4-6 introns"
                     /experiment="EXISTENCE:direct assay:GO:0004129 cytochrome-coxidase activity [PMID:1331058|PMID:7851399]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005751mitochondrial respiratory chain complex IV [PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0006123mitochondrial electron transport, cytochrome c to oxygen[PMID:1331058]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:9724417]"
                     /gbkey="CDS"
                     /locus_tag="Q0045"
                     /product="cytochrome c oxidase subunit 1"
                     /protein_id="NP_009305.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             23612..23746
                     /label="COX1"
                     /ID="cds-NP_009305.1"
                     /Parent="rna-Q0045"
                     /Dbxref="Genbank:NP_009305.1"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /Name="NP_009305.1"
                     /Note="Subunit I of cytochrome c oxidase (Complex IV);Complex IV is the terminal member of the mitochondrialinner membrane electron transport chain; one of threemitochondrially-encoded subunits; number of introns indifferent strains varies from 2 to 8, with most strainshaving 4-6 introns"
                     /experiment="EXISTENCE:direct assay:GO:0004129 cytochrome-coxidase activity [PMID:1331058|PMID:7851399]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005751mitochondrial respiratory chain complex IV [PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0006123mitochondrial electron transport, cytochrome c to oxygen[PMID:1331058]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:9724417]"
                     /gbkey="CDS"
                     /locus_tag="Q0045"
                     /product="cytochrome c oxidase subunit 1"
                     /protein_id="NP_009305.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             25318..25342
                     /label="COX1"
                     /ID="cds-NP_009305.1"
                     /Parent="rna-Q0045"
                     /Dbxref="Genbank:NP_009305.1"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /Name="NP_009305.1"
                     /Note="Subunit I of cytochrome c oxidase (Complex IV);Complex IV is the terminal member of the mitochondrialinner membrane electron transport chain; one of threemitochondrially-encoded subunits; number of introns indifferent strains varies from 2 to 8, with most strainshaving 4-6 introns"
                     /experiment="EXISTENCE:direct assay:GO:0004129 cytochrome-coxidase activity [PMID:1331058|PMID:7851399]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005751mitochondrial respiratory chain complex IV [PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0006123mitochondrial electron transport, cytochrome c to oxygen[PMID:1331058]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:9724417]"
                     /gbkey="CDS"
                     /locus_tag="Q0045"
                     /product="cytochrome c oxidase subunit 1"
                     /protein_id="NP_009305.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             26229..26701
                     /label="COX1"
                     /ID="cds-NP_009305.1"
                     /Parent="rna-Q0045"
                     /Dbxref="Genbank:NP_009305.1"
                     /Dbxref="GeneID:854598"
                     /Dbxref="SGD:S000007260"
                     /Name="NP_009305.1"
                     /Note="Subunit I of cytochrome c oxidase (Complex IV);Complex IV is the terminal member of the mitochondrialinner membrane electron transport chain; one of threemitochondrially-encoded subunits; number of introns indifferent strains varies from 2 to 8, with most strainshaving 4-6 introns"
                     /experiment="EXISTENCE:direct assay:GO:0004129 cytochrome-coxidase activity [PMID:1331058|PMID:7851399]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005751mitochondrial respiratory chain complex IV [PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0006123mitochondrial electron transport, cytochrome c to oxygen[PMID:1331058]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:9724417]"
                     /gbkey="CDS"
                     /locus_tag="Q0045"
                     /product="cytochrome c oxidase subunit 1"
                     /protein_id="NP_009305.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     gene            13818..23167
                     /label="AI5_ALPHA"
                     /ID="gene-Q0070"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /Name="AI5_ALPHA"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0070"
                     /source="RefSeq"
     mRNA            13818..23167
                     /label="AI5_ALPHA"
                     /ID="rna-Q0070"
                     /Parent="gene-Q0070"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /gbkey="mRNA"
                     /locus_tag="Q0070"
                     /source="RefSeq"
     exon            13818..13986
                     /label="AI5_ALPHA"
                     /ID="exon-Q0070-1"
                     /Parent="rna-Q0070"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /gbkey="mRNA"
                     /locus_tag="Q0070"
                     /source="RefSeq"
     exon            16435..16470
                     /label="AI5_ALPHA"
                     /ID="exon-Q0070-2"
                     /Parent="rna-Q0070"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /gbkey="mRNA"
                     /locus_tag="Q0070"
                     /source="RefSeq"
     exon            18954..18991
                     /label="AI5_ALPHA"
                     /ID="exon-Q0070-3"
                     /Parent="rna-Q0070"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /gbkey="mRNA"
                     /locus_tag="Q0070"
                     /source="RefSeq"
     exon            20508..20984
                     /label="AI5_ALPHA"
                     /ID="exon-Q0070-4"
                     /Parent="rna-Q0070"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /gbkey="mRNA"
                     /locus_tag="Q0070"
                     /source="RefSeq"
     exon            21995..23167
                     /label="AI5_ALPHA"
                     /ID="exon-Q0070-5"
                     /Parent="rna-Q0070"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /gbkey="mRNA"
                     /locus_tag="Q0070"
                     /source="RefSeq"
     CDS             13818..13986
                     /label="AI5_ALPHA"
                     /ID="cds-NP_009306.1"
                     /Parent="rna-Q0070"
                     /Dbxref="Genbank:NP_009306.1"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /Name="NP_009306.1"
                     /Note="Endonuclease I-SceIV; involved in intron mobility;encoded by a mobile group I intron within the mitochondrialCOX1 gene"
                     /experiment="EXISTENCE:mutant phenotype:GO:0004519endonuclease activity [PMID:1314207]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006316 movementof group I intron [PMID:1314207]"
                     /gbkey="CDS"
                     /locus_tag="Q0070"
                     /product="intron-encoded DNA endonuclease aI5 alpha"
                     /protein_id="NP_009306.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             16435..16470
                     /label="AI5_ALPHA"
                     /ID="cds-NP_009306.1"
                     /Parent="rna-Q0070"
                     /Dbxref="Genbank:NP_009306.1"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /Name="NP_009306.1"
                     /Note="Endonuclease I-SceIV; involved in intron mobility;encoded by a mobile group I intron within the mitochondrialCOX1 gene"
                     /experiment="EXISTENCE:mutant phenotype:GO:0004519endonuclease activity [PMID:1314207]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006316 movementof group I intron [PMID:1314207]"
                     /gbkey="CDS"
                     /locus_tag="Q0070"
                     /product="intron-encoded DNA endonuclease aI5 alpha"
                     /protein_id="NP_009306.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     CDS             18954..18991
                     /label="AI5_ALPHA"
                     /ID="cds-NP_009306.1"
                     /Parent="rna-Q0070"
                     /Dbxref="Genbank:NP_009306.1"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /Name="NP_009306.1"
                     /Note="Endonuclease I-SceIV; involved in intron mobility;encoded by a mobile group I intron within the mitochondrialCOX1 gene"
                     /experiment="EXISTENCE:mutant phenotype:GO:0004519endonuclease activity [PMID:1314207]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006316 movementof group I intron [PMID:1314207]"
                     /gbkey="CDS"
                     /locus_tag="Q0070"
                     /product="intron-encoded DNA endonuclease aI5 alpha"
                     /protein_id="NP_009306.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     CDS             20508..20984
                     /label="AI5_ALPHA"
                     /ID="cds-NP_009306.1"
                     /Parent="rna-Q0070"
                     /Dbxref="Genbank:NP_009306.1"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /Name="NP_009306.1"
                     /Note="Endonuclease I-SceIV; involved in intron mobility;encoded by a mobile group I intron within the mitochondrialCOX1 gene"
                     /experiment="EXISTENCE:mutant phenotype:GO:0004519endonuclease activity [PMID:1314207]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006316 movementof group I intron [PMID:1314207]"
                     /gbkey="CDS"
                     /locus_tag="Q0070"
                     /product="intron-encoded DNA endonuclease aI5 alpha"
                     /protein_id="NP_009306.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             21995..23167
                     /label="AI5_ALPHA"
                     /ID="cds-NP_009306.1"
                     /Parent="rna-Q0070"
                     /Dbxref="Genbank:NP_009306.1"
                     /Dbxref="GeneID:854597"
                     /Dbxref="SGD:S000007265"
                     /Name="NP_009306.1"
                     /Note="Endonuclease I-SceIV; involved in intron mobility;encoded by a mobile group I intron within the mitochondrialCOX1 gene"
                     /experiment="EXISTENCE:mutant phenotype:GO:0004519endonuclease activity [PMID:1314207]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006316 movementof group I intron [PMID:1314207]"
                     /gbkey="CDS"
                     /locus_tag="Q0070"
                     /product="intron-encoded DNA endonuclease aI5 alpha"
                     /protein_id="NP_009306.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     gene            13818..21935
                     /label="AI4"
                     /ID="gene-Q0065"
                     /Dbxref="GeneID:854596"
                     /Dbxref="SGD:S000007264"
                     /Name="AI4"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0065"
                     /source="RefSeq"
     mRNA            13818..21935
                     /label="AI4"
                     /ID="rna-Q0065"
                     /Parent="gene-Q0065"
                     /Dbxref="GeneID:854596"
                     /Dbxref="SGD:S000007264"
                     /gbkey="mRNA"
                     /locus_tag="Q0065"
                     /source="RefSeq"
     exon            13818..13986
                     /label="AI4"
                     /ID="exon-Q0065-1"
                     /Parent="rna-Q0065"
                     /Dbxref="GeneID:854596"
                     /Dbxref="SGD:S000007264"
                     /gbkey="mRNA"
                     /locus_tag="Q0065"
                     /source="RefSeq"
     exon            16435..16470
                     /label="AI4"
                     /ID="exon-Q0065-2"
                     /Parent="rna-Q0065"
                     /Dbxref="GeneID:854596"
                     /Dbxref="SGD:S000007264"
                     /gbkey="mRNA"
                     /locus_tag="Q0065"
                     /source="RefSeq"
     exon            18954..18991
                     /label="AI4"
                     /ID="exon-Q0065-3"
                     /Parent="rna-Q0065"
                     /Dbxref="GeneID:854596"
                     /Dbxref="SGD:S000007264"
                     /gbkey="mRNA"
                     /locus_tag="Q0065"
                     /source="RefSeq"
     exon            20508..21935
                     /label="AI4"
                     /ID="exon-Q0065-4"
                     /Parent="rna-Q0065"
                     /Dbxref="GeneID:854596"
                     /Dbxref="SGD:S000007264"
                     /gbkey="mRNA"
                     /locus_tag="Q0065"
                     /source="RefSeq"
     CDS             13818..13986
                     /label="AI4"
                     /ID="cds-NP_009307.2"
                     /Parent="rna-Q0065"
                     /Dbxref="Genbank:NP_009307.2"
                     /Dbxref="GeneID:854596"
                     /Dbxref="SGD:S000007264"
                     /Name="NP_009307.2"
                     /Note="Endonuclease I-SceII; encoded by a mobile group Iintron within the mitochondrial COX1 gene; intron isnormally spliced by the BI4p maturase but AI4p can mutateto acquire the same maturase activity"
                     /experiment="EXISTENCE:direct assay:GO:0004519 endonucleaseactivity [PMID:7588637]"
                     /experiment="EXISTENCE:direct assay:GO:0008380 RNA splicing[PMID:7588637]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006314 intronhoming [PMID:2536592]"
                     /gbkey="CDS"
                     /locus_tag="Q0065"
                     /product="intron-encoded DNA endonuclease aI4"
                     /protein_id="NP_009307.2"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             16435..16470
                     /label="AI4"
                     /ID="cds-NP_009307.2"
                     /Parent="rna-Q0065"
                     /Dbxref="Genbank:NP_009307.2"
                     /Dbxref="GeneID:854596"
                     /Dbxref="SGD:S000007264"
                     /Name="NP_009307.2"
                     /Note="Endonuclease I-SceII; encoded by a mobile group Iintron within the mitochondrial COX1 gene; intron isnormally spliced by the BI4p maturase but AI4p can mutateto acquire the same maturase activity"
                     /experiment="EXISTENCE:direct assay:GO:0004519 endonucleaseactivity [PMID:7588637]"
                     /experiment="EXISTENCE:direct assay:GO:0008380 RNA splicing[PMID:7588637]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006314 intronhoming [PMID:2536592]"
                     /gbkey="CDS"
                     /locus_tag="Q0065"
                     /product="intron-encoded DNA endonuclease aI4"
                     /protein_id="NP_009307.2"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     CDS             18954..18991
                     /label="AI4"
                     /ID="cds-NP_009307.2"
                     /Parent="rna-Q0065"
                     /Dbxref="Genbank:NP_009307.2"
                     /Dbxref="GeneID:854596"
                     /Dbxref="SGD:S000007264"
                     /Name="NP_009307.2"
                     /Note="Endonuclease I-SceII; encoded by a mobile group Iintron within the mitochondrial COX1 gene; intron isnormally spliced by the BI4p maturase but AI4p can mutateto acquire the same maturase activity"
                     /experiment="EXISTENCE:direct assay:GO:0004519 endonucleaseactivity [PMID:7588637]"
                     /experiment="EXISTENCE:direct assay:GO:0008380 RNA splicing[PMID:7588637]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006314 intronhoming [PMID:2536592]"
                     /gbkey="CDS"
                     /locus_tag="Q0065"
                     /product="intron-encoded DNA endonuclease aI4"
                     /protein_id="NP_009307.2"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     CDS             20508..21935
                     /label="AI4"
                     /ID="cds-NP_009307.2"
                     /Parent="rna-Q0065"
                     /Dbxref="Genbank:NP_009307.2"
                     /Dbxref="GeneID:854596"
                     /Dbxref="SGD:S000007264"
                     /Name="NP_009307.2"
                     /Note="Endonuclease I-SceII; encoded by a mobile group Iintron within the mitochondrial COX1 gene; intron isnormally spliced by the BI4p maturase but AI4p can mutateto acquire the same maturase activity"
                     /experiment="EXISTENCE:direct assay:GO:0004519 endonucleaseactivity [PMID:7588637]"
                     /experiment="EXISTENCE:direct assay:GO:0008380 RNA splicing[PMID:7588637]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006314 intronhoming [PMID:2536592]"
                     /gbkey="CDS"
                     /locus_tag="Q0065"
                     /product="intron-encoded DNA endonuclease aI4"
                     /protein_id="NP_009307.2"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     gene            13818..19996
                     /label="AI3"
                     /ID="gene-Q0060"
                     /Dbxref="GeneID:854595"
                     /Dbxref="SGD:S000007263"
                     /Name="AI3"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0060"
                     /source="RefSeq"
     mRNA            13818..19996
                     /label="AI3"
                     /ID="rna-Q0060"
                     /Parent="gene-Q0060"
                     /Dbxref="GeneID:854595"
                     /Dbxref="SGD:S000007263"
                     /gbkey="mRNA"
                     /locus_tag="Q0060"
                     /source="RefSeq"
     exon            13818..13986
                     /label="AI3"
                     /ID="exon-Q0060-1"
                     /Parent="rna-Q0060"
                     /Dbxref="GeneID:854595"
                     /Dbxref="SGD:S000007263"
                     /gbkey="mRNA"
                     /locus_tag="Q0060"
                     /source="RefSeq"
     exon            16435..16470
                     /label="AI3"
                     /ID="exon-Q0060-2"
                     /Parent="rna-Q0060"
                     /Dbxref="GeneID:854595"
                     /Dbxref="SGD:S000007263"
                     /gbkey="mRNA"
                     /locus_tag="Q0060"
                     /source="RefSeq"
     exon            18954..19996
                     /label="AI3"
                     /ID="exon-Q0060-3"
                     /Parent="rna-Q0060"
                     /Dbxref="GeneID:854595"
                     /Dbxref="SGD:S000007263"
                     /gbkey="mRNA"
                     /locus_tag="Q0060"
                     /source="RefSeq"
     CDS             13818..13986
                     /label="AI3"
                     /ID="cds-NP_009308.2"
                     /Parent="rna-Q0060"
                     /Dbxref="Genbank:NP_009308.2"
                     /Dbxref="GeneID:854595"
                     /Dbxref="SGD:S000007263"
                     /Name="NP_009308.2"
                     /Note="Endonuclease I-SceIII; encoded by a mobile group Iintron within the mitochondrial COX1 gene"
                     /experiment="EXISTENCE:direct assay:GO:0004519 endonucleaseactivity [PMID:7797552]"
                     /gbkey="CDS"
                     /locus_tag="Q0060"
                     /product="intron-encoded DNA endonuclease aI3"
                     /protein_id="NP_009308.2"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             16435..16470
                     /label="AI3"
                     /ID="cds-NP_009308.2"
                     /Parent="rna-Q0060"
                     /Dbxref="Genbank:NP_009308.2"
                     /Dbxref="GeneID:854595"
                     /Dbxref="SGD:S000007263"
                     /Name="NP_009308.2"
                     /Note="Endonuclease I-SceIII; encoded by a mobile group Iintron within the mitochondrial COX1 gene"
                     /experiment="EXISTENCE:direct assay:GO:0004519 endonucleaseactivity [PMID:7797552]"
                     /gbkey="CDS"
                     /locus_tag="Q0060"
                     /product="intron-encoded DNA endonuclease aI3"
                     /protein_id="NP_009308.2"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     CDS             18954..19996
                     /label="AI3"
                     /ID="cds-NP_009308.2"
                     /Parent="rna-Q0060"
                     /Dbxref="Genbank:NP_009308.2"
                     /Dbxref="GeneID:854595"
                     /Dbxref="SGD:S000007263"
                     /Name="NP_009308.2"
                     /Note="Endonuclease I-SceIII; encoded by a mobile group Iintron within the mitochondrial COX1 gene"
                     /experiment="EXISTENCE:direct assay:GO:0004519 endonucleaseactivity [PMID:7797552]"
                     /gbkey="CDS"
                     /locus_tag="Q0060"
                     /product="intron-encoded DNA endonuclease aI3"
                     /protein_id="NP_009308.2"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     gene            13818..18830
                     /label="AI2"
                     /ID="gene-Q0055"
                     /Dbxref="GeneID:854594"
                     /Dbxref="SGD:S000007262"
                     /Name="AI2"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0055"
                     /source="RefSeq"
     mRNA            13818..18830
                     /label="AI2"
                     /ID="rna-Q0055"
                     /Parent="gene-Q0055"
                     /Dbxref="GeneID:854594"
                     /Dbxref="SGD:S000007262"
                     /gbkey="mRNA"
                     /locus_tag="Q0055"
                     /source="RefSeq"
     exon            13818..13986
                     /label="AI2"
                     /ID="exon-Q0055-1"
                     /Parent="rna-Q0055"
                     /Dbxref="GeneID:854594"
                     /Dbxref="SGD:S000007262"
                     /gbkey="mRNA"
                     /locus_tag="Q0055"
                     /source="RefSeq"
     exon            16435..18830
                     /label="AI2"
                     /ID="exon-Q0055-2"
                     /Parent="rna-Q0055"
                     /Dbxref="GeneID:854594"
                     /Dbxref="SGD:S000007262"
                     /gbkey="mRNA"
                     /locus_tag="Q0055"
                     /source="RefSeq"
     CDS             13818..13986
                     /label="AI2"
                     /ID="cds-NP_009309.1"
                     /Parent="rna-Q0055"
                     /Dbxref="Genbank:NP_009309.1"
                     /Dbxref="GeneID:854594"
                     /Dbxref="SGD:S000007262"
                     /Name="NP_009309.1"
                     /Note="Reverse transcriptase required for splicing of theCOX1 pre-mRNA; encoded by a mobile group II intron withinthe mitochondrial COX1 gene"
                     /experiment="EXISTENCE:direct assay:GO:0003964 RNA-directedDNA polymerase activity [PMID:7681727]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:7681727]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006314 intronhoming [PMID:14612420]"
                     /gbkey="CDS"
                     /locus_tag="Q0055"
                     /product="intron-encoded reverse transcriptase aI2"
                     /protein_id="NP_009309.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             16435..18830
                     /label="AI2"
                     /ID="cds-NP_009309.1"
                     /Parent="rna-Q0055"
                     /Dbxref="Genbank:NP_009309.1"
                     /Dbxref="GeneID:854594"
                     /Dbxref="SGD:S000007262"
                     /Name="NP_009309.1"
                     /Note="Reverse transcriptase required for splicing of theCOX1 pre-mRNA; encoded by a mobile group II intron withinthe mitochondrial COX1 gene"
                     /experiment="EXISTENCE:direct assay:GO:0003964 RNA-directedDNA polymerase activity [PMID:7681727]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:7681727]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006314 intronhoming [PMID:14612420]"
                     /gbkey="CDS"
                     /locus_tag="Q0055"
                     /product="intron-encoded reverse transcriptase aI2"
                     /protein_id="NP_009309.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     gene            13818..16322
                     /label="AI1"
                     /ID="gene-Q0050"
                     /Dbxref="GeneID:854593"
                     /Dbxref="SGD:S000007261"
                     /Name="AI1"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0050"
                     /source="RefSeq"
     mRNA            13818..16322
                     /label="AI1"
                     /ID="rna-Q0050"
                     /Parent="gene-Q0050"
                     /Dbxref="GeneID:854593"
                     /Dbxref="SGD:S000007261"
                     /gbkey="mRNA"
                     /locus_tag="Q0050"
                     /source="RefSeq"
     exon            13818..16322
                     /label="AI1"
                     /ID="exon-Q0050-1"
                     /Parent="rna-Q0050"
                     /Dbxref="GeneID:854593"
                     /Dbxref="SGD:S000007261"
                     /gbkey="mRNA"
                     /locus_tag="Q0050"
                     /source="RefSeq"
     CDS             13818..16322
                     /label="AI1"
                     /ID="cds-NP_009310.1"
                     /Parent="rna-Q0050"
                     /Dbxref="Genbank:NP_009310.1"
                     /Dbxref="GeneID:854593"
                     /Dbxref="SGD:S000007261"
                     /Name="NP_009310.1"
                     /Note="Reverse transcriptase required for splicing of theCOX1 pre-mRNA; encoded by a mobile group II intron withinthe mitochondrial COX1 gene"
                     /experiment="EXISTENCE:direct assay:GO:0006315 homing ofgroup II introns [PMID:11687644]"
                     /experiment="EXISTENCE:direct assay:GO:0006397 mRNAprocessing [PMID:11687644]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0003964RNA-directed DNA polymerase activity [PMID:15925309]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0005739mitochondrion [PMID:15925309]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006278RNA-dependent DNA biosynthetic process [PMID:15925309]"
                     /gbkey="CDS"
                     /locus_tag="Q0050"
                     /product="intron-encoded reverse transcriptase aI1"
                     /protein_id="NP_009310.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     gene            24156..25255
                     /label="AI5_BETA"
                     /ID="gene-Q0075"
                     /Dbxref="GeneID:854599"
                     /Dbxref="SGD:S000007266"
                     /Name="AI5_BETA"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0075"
                     /source="RefSeq"
     mRNA            24156..25255
                     /label="AI5_BETA"
                     /ID="rna-Q0075"
                     /Parent="gene-Q0075"
                     /Dbxref="GeneID:854599"
                     /Dbxref="SGD:S000007266"
                     /gbkey="mRNA"
                     /locus_tag="Q0075"
                     /source="RefSeq"
     exon            24156..24870
                     /label="AI5_BETA"
                     /ID="exon-Q0075-1"
                     /Parent="rna-Q0075"
                     /Dbxref="GeneID:854599"
                     /Dbxref="SGD:S000007266"
                     /gbkey="mRNA"
                     /locus_tag="Q0075"
                     /source="RefSeq"
     exon            24906..25255
                     /label="AI5_BETA"
                     /ID="exon-Q0075-2"
                     /Parent="rna-Q0075"
                     /Dbxref="GeneID:854599"
                     /Dbxref="SGD:S000007266"
                     /gbkey="mRNA"
                     /locus_tag="Q0075"
                     /source="RefSeq"
     CDS             24156..24870
                     /label="AI5_BETA"
                     /ID="cds-NP_009311.2"
                     /Parent="rna-Q0075"
                     /Dbxref="Genbank:NP_009311.2"
                     /Dbxref="GeneID:854599"
                     /Dbxref="SGD:S000007266"
                     /Name="NP_009311.2"
                     /Note="hypothetical protein; encoded within an intron ofthe mitochondrial COX1 gene; translational initiation codonis predicted to be ATA rather than ATG"
                     /gbkey="CDS"
                     /locus_tag="Q0075"
                     /product="intron-encoded DNA endonuclease aI5 beta"
                     /protein_id="NP_009311.2"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             24906..25255
                     /label="AI5_BETA"
                     /ID="cds-NP_009311.2"
                     /Parent="rna-Q0075"
                     /Dbxref="Genbank:NP_009311.2"
                     /Dbxref="GeneID:854599"
                     /Dbxref="SGD:S000007266"
                     /Name="NP_009311.2"
                     /Note="hypothetical protein; encoded within an intron ofthe mitochondrial COX1 gene; translational initiation codonis predicted to be ATA rather than ATG"
                     /gbkey="CDS"
                     /locus_tag="Q0075"
                     /product="intron-encoded DNA endonuclease aI5 beta"
                     /protein_id="NP_009311.2"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     gene            27666..27812
                     /label="ATP8"
                     /ID="gene-Q0080"
                     /Dbxref="GeneID:854600"
                     /Dbxref="SGD:S000007267"
                     /Name="ATP8"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /gene_synonym="AAP1"
                     /locus_tag="Q0080"
                     /source="RefSeq"
     mRNA            27666..27812
                     /label="ATP8"
                     /ID="rna-Q0080"
                     /Parent="gene-Q0080"
                     /Dbxref="GeneID:854600"
                     /Dbxref="SGD:S000007267"
                     /gbkey="mRNA"
                     /locus_tag="Q0080"
                     /source="RefSeq"
     exon            27666..27812
                     /label="ATP8"
                     /ID="exon-Q0080-1"
                     /Parent="rna-Q0080"
                     /Dbxref="GeneID:854600"
                     /Dbxref="SGD:S000007267"
                     /gbkey="mRNA"
                     /locus_tag="Q0080"
                     /source="RefSeq"
     CDS             27666..27812
                     /label="ATP8"
                     /ID="cds-NP_009312.1"
                     /Parent="rna-Q0080"
                     /Dbxref="Genbank:NP_009312.1"
                     /Dbxref="GeneID:854600"
                     /Dbxref="SGD:S000007267"
                     /Name="NP_009312.1"
                     /Note="Subunit 8 of the F0 sector of mitochondrial F1F0 ATPsynthase; encoded on the mitochondrial genome; ATP8 andATP6 mRNAs are not translated in the absence of the F1sector of ATPase"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0015986 ATPsynthesis coupled proton transport [PMID:20691145]"
                     /experiment="EXISTENCE:direct assay:GO:0046933proton-transporting ATP synthase activity, rotationalmechanism [PMID:20691145]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0000276mitochondrial proton-transporting ATP synthase complex,coupling factor F(o) [PMID:8662204]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0015986 ATPsynthesis coupled proton transport [PMID:8662204]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0046933proton-transporting ATP synthase activity, rotationalmechanism [PMID:8662204]"
                     /gbkey="CDS"
                     /locus_tag="Q0080"
                     /product="F1F0 ATP synthase subunit 8"
                     /protein_id="NP_009312.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     gene            28487..29266
                     /label="ATP6"
                     /ID="gene-Q0085"
                     /Dbxref="GeneID:854601"
                     /Dbxref="SGD:S000007268"
                     /Name="ATP6"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /gene_synonym="OLI2"
                     /gene_synonym="OLI4"
                     /gene_synonym="PHO1"
                     /locus_tag="Q0085"
                     /source="RefSeq"
     mRNA            28487..29266
                     /label="ATP6"
                     /ID="rna-Q0085"
                     /Parent="gene-Q0085"
                     /Dbxref="GeneID:854601"
                     /Dbxref="SGD:S000007268"
                     /gbkey="mRNA"
                     /locus_tag="Q0085"
                     /source="RefSeq"
     exon            28487..29266
                     /label="ATP6"
                     /ID="exon-Q0085-1"
                     /Parent="rna-Q0085"
                     /Dbxref="GeneID:854601"
                     /Dbxref="SGD:S000007268"
                     /gbkey="mRNA"
                     /locus_tag="Q0085"
                     /source="RefSeq"
     CDS             28487..29266
                     /label="ATP6"
                     /ID="cds-NP_009313.1"
                     /Parent="rna-Q0085"
                     /Dbxref="Genbank:NP_009313.1"
                     /Dbxref="GeneID:854601"
                     /Dbxref="SGD:S000007268"
                     /Name="NP_009313.1"
                     /Note="Subunit a of the F0 sector of mitochondrial F1F0 ATPsynthase; mitochondrially encoded; translation isspecifically activated by Atp22p; ATP6 and ATP8 mRNAs arenot translated in the absence of the F1 sector of ATPase;mutations in human ortholog MT-ATP6 are associated withneurodegenerative disorders such as Neurogenic Ataxia andRetinitis Pigmentosa (NARP), Leigh syndrome (LS),Charcot-Marie-Tooth (CMT), and ataxia telangiectasia"
                     /experiment="EXISTENCE:direct assay:GO:0000276mitochondrial proton-transporting ATP synthase complex,coupling factor F(o) [PMID:18722382]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0015986 ATPsynthesis coupled proton transport [PMID:20691145]"
                     /experiment="EXISTENCE:direct assay:GO:0046933proton-transporting ATP synthase activity, rotationalmechanism [PMID:20691145]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0015986 ATPsynthesis coupled proton transport[PMID:17940284|PMID:6251866]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0046933proton-transporting ATP synthase activity, rotationalmechanism [PMID:17940284|PMID:6251866]"
                     /gbkey="CDS"
                     /locus_tag="Q0085"
                     /product="F1F0 ATP synthase subunit a"
                     /protein_id="NP_009313.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     rep_origin      30220..30594
                     /label="Untitled Feature"
                     /ID="id-NC_001224.1:30220..30594"
                     /Dbxref="SGD:S000029673"
                     /Note="ORI7; Mitochondrial origin of replication"
                     /gbkey="rep_origin"
                     /source="RefSeq"
     rep_origin      32231..32501
                     /label="Untitled Feature"
                     /ID="id-NC_001224.1:32231..32501"
                     /Dbxref="SGD:S000029668"
                     /Note="ORI2; Mitochondrial origin of replication"
                     /gbkey="rep_origin"
                     /source="RefSeq"
     gene            35373..35444
                     /label="tE(UUC)Q"
                     /ID="gene-tE(UUC)Q"
                     /Dbxref="GeneID:854603"
                     /Dbxref="SGD:S000007318"
                     /Name="tE(UUC)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tE(UUC)Q"
                     /source="RefSeq"
     tRNA            35373..35444
                     /label="tE(UUC)Q"
                     /ID="rna-tE(UUC)Q"
                     /Parent="gene-tE(UUC)Q"
                     /Dbxref="GeneID:854603"
                     /Dbxref="SGD:S000007318"
                     /Note="Mitochondrial glutamate tRNA (tRNA-Glu); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tE(UUC)Q"
                     /product="tRNA-Glu"
                     /source="RefSeq"
     exon            35373..35444
                     /label="tE(UUC)Q"
                     /ID="exon-tE(UUC)Q-1"
                     /Parent="rna-tE(UUC)Q"
                     /Dbxref="GeneID:854603"
                     /Dbxref="SGD:S000007318"
                     /Note="Mitochondrial glutamate tRNA (tRNA-Glu); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tE(UUC)Q"
                     /product="tRNA-Glu"
                     /source="RefSeq"
     gene            36540..43647
                     /label="COB"
                     /ID="gene-Q0105"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /Name="COB"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /gene_synonym="COB1"
                     /gene_synonym="CYTB"
                     /locus_tag="Q0105"
                     /source="RefSeq"
     mRNA            36540..43647
                     /label="COB"
                     /ID="rna-Q0105"
                     /Parent="gene-Q0105"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /gbkey="mRNA"
                     /locus_tag="Q0105"
                     /source="RefSeq"
     exon            36540..36954
                     /label="COB"
                     /ID="exon-Q0105-1"
                     /Parent="rna-Q0105"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /gbkey="mRNA"
                     /locus_tag="Q0105"
                     /source="RefSeq"
     exon            37723..37736
                     /label="COB"
                     /ID="exon-Q0105-2"
                     /Parent="rna-Q0105"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /gbkey="mRNA"
                     /locus_tag="Q0105"
                     /source="RefSeq"
     exon            39141..39217
                     /label="COB"
                     /ID="exon-Q0105-3"
                     /Parent="rna-Q0105"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /gbkey="mRNA"
                     /locus_tag="Q0105"
                     /source="RefSeq"
     exon            40841..41090
                     /label="COB"
                     /ID="exon-Q0105-4"
                     /Parent="rna-Q0105"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /gbkey="mRNA"
                     /locus_tag="Q0105"
                     /source="RefSeq"
     exon            42508..42558
                     /label="COB"
                     /ID="exon-Q0105-5"
                     /Parent="rna-Q0105"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /gbkey="mRNA"
                     /locus_tag="Q0105"
                     /source="RefSeq"
     exon            43297..43647
                     /label="COB"
                     /ID="exon-Q0105-6"
                     /Parent="rna-Q0105"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /gbkey="mRNA"
                     /locus_tag="Q0105"
                     /source="RefSeq"
     CDS             36540..36954
                     /label="COB"
                     /ID="cds-NP_009315.1"
                     /Parent="rna-Q0105"
                     /Dbxref="Genbank:NP_009315.1"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /Name="NP_009315.1"
                     /Note="Cytochrome b; mitochondrially-encoded subunit ofubiquinol-cytochrome c reductase complex which includesCobp, Rip1p, Cyt1p, Cor1p, Qcr2p, Qcr6p, Qcr7p, Qcr8p,Qcr9p, and Qcr10p; number of introns varies between strainsfrom 0 to 6, with most having 5"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005750mitochondrial respiratory chain complex III[PMID:10873857|PMID:391561]"
                     /experiment="EXISTENCE:direct assay:GO:0008121ubiquinol-cytochrome-c reductase activity [PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006122mitochondrial electron transport, ubiquinol to cytochrome c[PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:391561]"
                     /gbkey="CDS"
                     /locus_tag="Q0105"
                     /product="cytochrome b"
                     /protein_id="NP_009315.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             37723..37736
                     /label="COB"
                     /ID="cds-NP_009315.1"
                     /Parent="rna-Q0105"
                     /Dbxref="Genbank:NP_009315.1"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /Name="NP_009315.1"
                     /Note="Cytochrome b; mitochondrially-encoded subunit ofubiquinol-cytochrome c reductase complex which includesCobp, Rip1p, Cyt1p, Cor1p, Qcr2p, Qcr6p, Qcr7p, Qcr8p,Qcr9p, and Qcr10p; number of introns varies between strainsfrom 0 to 6, with most having 5"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005750mitochondrial respiratory chain complex III[PMID:10873857|PMID:391561]"
                     /experiment="EXISTENCE:direct assay:GO:0008121ubiquinol-cytochrome-c reductase activity [PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006122mitochondrial electron transport, ubiquinol to cytochrome c[PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:391561]"
                     /gbkey="CDS"
                     /locus_tag="Q0105"
                     /product="cytochrome b"
                     /protein_id="NP_009315.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     CDS             39141..39217
                     /label="COB"
                     /ID="cds-NP_009315.1"
                     /Parent="rna-Q0105"
                     /Dbxref="Genbank:NP_009315.1"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /Name="NP_009315.1"
                     /Note="Cytochrome b; mitochondrially-encoded subunit ofubiquinol-cytochrome c reductase complex which includesCobp, Rip1p, Cyt1p, Cor1p, Qcr2p, Qcr6p, Qcr7p, Qcr8p,Qcr9p, and Qcr10p; number of introns varies between strainsfrom 0 to 6, with most having 5"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005750mitochondrial respiratory chain complex III[PMID:10873857|PMID:391561]"
                     /experiment="EXISTENCE:direct assay:GO:0008121ubiquinol-cytochrome-c reductase activity [PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006122mitochondrial electron transport, ubiquinol to cytochrome c[PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:391561]"
                     /gbkey="CDS"
                     /locus_tag="Q0105"
                     /product="cytochrome b"
                     /protein_id="NP_009315.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             40841..41090
                     /label="COB"
                     /ID="cds-NP_009315.1"
                     /Parent="rna-Q0105"
                     /Dbxref="Genbank:NP_009315.1"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /Name="NP_009315.1"
                     /Note="Cytochrome b; mitochondrially-encoded subunit ofubiquinol-cytochrome c reductase complex which includesCobp, Rip1p, Cyt1p, Cor1p, Qcr2p, Qcr6p, Qcr7p, Qcr8p,Qcr9p, and Qcr10p; number of introns varies between strainsfrom 0 to 6, with most having 5"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005750mitochondrial respiratory chain complex III[PMID:10873857|PMID:391561]"
                     /experiment="EXISTENCE:direct assay:GO:0008121ubiquinol-cytochrome-c reductase activity [PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006122mitochondrial electron transport, ubiquinol to cytochrome c[PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:391561]"
                     /gbkey="CDS"
                     /locus_tag="Q0105"
                     /product="cytochrome b"
                     /protein_id="NP_009315.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="1"
     CDS             42508..42558
                     /label="COB"
                     /ID="cds-NP_009315.1"
                     /Parent="rna-Q0105"
                     /Dbxref="Genbank:NP_009315.1"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /Name="NP_009315.1"
                     /Note="Cytochrome b; mitochondrially-encoded subunit ofubiquinol-cytochrome c reductase complex which includesCobp, Rip1p, Cyt1p, Cor1p, Qcr2p, Qcr6p, Qcr7p, Qcr8p,Qcr9p, and Qcr10p; number of introns varies between strainsfrom 0 to 6, with most having 5"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005750mitochondrial respiratory chain complex III[PMID:10873857|PMID:391561]"
                     /experiment="EXISTENCE:direct assay:GO:0008121ubiquinol-cytochrome-c reductase activity [PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006122mitochondrial electron transport, ubiquinol to cytochrome c[PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:391561]"
                     /gbkey="CDS"
                     /locus_tag="Q0105"
                     /product="cytochrome b"
                     /protein_id="NP_009315.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             43297..43647
                     /label="COB"
                     /ID="cds-NP_009315.1"
                     /Parent="rna-Q0105"
                     /Dbxref="Genbank:NP_009315.1"
                     /Dbxref="GeneID:854583"
                     /Dbxref="SGD:S000007270"
                     /Name="NP_009315.1"
                     /Note="Cytochrome b; mitochondrially-encoded subunit ofubiquinol-cytochrome c reductase complex which includesCobp, Rip1p, Cyt1p, Cor1p, Qcr2p, Qcr6p, Qcr7p, Qcr8p,Qcr9p, and Qcr10p; number of introns varies between strainsfrom 0 to 6, with most having 5"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005750mitochondrial respiratory chain complex III[PMID:10873857|PMID:391561]"
                     /experiment="EXISTENCE:direct assay:GO:0008121ubiquinol-cytochrome-c reductase activity [PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006122mitochondrial electron transport, ubiquinol to cytochrome c[PMID:391561]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:391561]"
                     /gbkey="CDS"
                     /locus_tag="Q0105"
                     /product="cytochrome b"
                     /protein_id="NP_009315.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     gene            36540..42251
                     /label="BI4"
                     /ID="gene-Q0120"
                     /Dbxref="GeneID:854582"
                     /Dbxref="SGD:S000007273"
                     /Name="BI4"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0120"
                     /source="RefSeq"
     mRNA            36540..42251
                     /label="BI4"
                     /ID="rna-Q0120"
                     /Parent="gene-Q0120"
                     /Dbxref="GeneID:854582"
                     /Dbxref="SGD:S000007273"
                     /gbkey="mRNA"
                     /locus_tag="Q0120"
                     /source="RefSeq"
     exon            36540..36954
                     /label="BI4"
                     /ID="exon-Q0120-1"
                     /Parent="rna-Q0120"
                     /Dbxref="GeneID:854582"
                     /Dbxref="SGD:S000007273"
                     /gbkey="mRNA"
                     /locus_tag="Q0120"
                     /source="RefSeq"
     exon            37723..37736
                     /label="BI4"
                     /ID="exon-Q0120-2"
                     /Parent="rna-Q0120"
                     /Dbxref="GeneID:854582"
                     /Dbxref="SGD:S000007273"
                     /gbkey="mRNA"
                     /locus_tag="Q0120"
                     /source="RefSeq"
     exon            39141..39217
                     /label="BI4"
                     /ID="exon-Q0120-3"
                     /Parent="rna-Q0120"
                     /Dbxref="GeneID:854582"
                     /Dbxref="SGD:S000007273"
                     /gbkey="mRNA"
                     /locus_tag="Q0120"
                     /source="RefSeq"
     exon            40841..42251
                     /label="BI4"
                     /ID="exon-Q0120-4"
                     /Parent="rna-Q0120"
                     /Dbxref="GeneID:854582"
                     /Dbxref="SGD:S000007273"
                     /gbkey="mRNA"
                     /locus_tag="Q0120"
                     /source="RefSeq"
     CDS             36540..36954
                     /label="BI4"
                     /ID="cds-NP_009316.1"
                     /Parent="rna-Q0120"
                     /Dbxref="Genbank:NP_009316.1"
                     /Dbxref="GeneID:854582"
                     /Dbxref="SGD:S000007273"
                     /Name="NP_009316.1"
                     /Note="Mitochondrial mRNA maturase; forms a complex withNam2p to mediate splicing of the bI4 intron of the COBgene; encoded by both exon and intron sequences ofpartially processed COB mRNA"
                     /experiment="EXISTENCE:direct assay:GO:0000372 Group Iintron splicing [PMID:11142386]"
                     /experiment="EXISTENCE:direct assay:GO:0003723 RNA binding[PMID:11142386]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:2875797]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0000372 Group Iintron splicing [PMID:11142386]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0090615mitochondrial mRNA processing [PMID:8917309]"
                     /gbkey="CDS"
                     /locus_tag="Q0120"
                     /product="intron-encoded RNA maturase bI4"
                     /protein_id="NP_009316.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             37723..37736
                     /label="BI4"
                     /ID="cds-NP_009316.1"
                     /Parent="rna-Q0120"
                     /Dbxref="Genbank:NP_009316.1"
                     /Dbxref="GeneID:854582"
                     /Dbxref="SGD:S000007273"
                     /Name="NP_009316.1"
                     /Note="Mitochondrial mRNA maturase; forms a complex withNam2p to mediate splicing of the bI4 intron of the COBgene; encoded by both exon and intron sequences ofpartially processed COB mRNA"
                     /experiment="EXISTENCE:direct assay:GO:0000372 Group Iintron splicing [PMID:11142386]"
                     /experiment="EXISTENCE:direct assay:GO:0003723 RNA binding[PMID:11142386]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:2875797]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0000372 Group Iintron splicing [PMID:11142386]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0090615mitochondrial mRNA processing [PMID:8917309]"
                     /gbkey="CDS"
                     /locus_tag="Q0120"
                     /product="intron-encoded RNA maturase bI4"
                     /protein_id="NP_009316.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     CDS             39141..39217
                     /label="BI4"
                     /ID="cds-NP_009316.1"
                     /Parent="rna-Q0120"
                     /Dbxref="Genbank:NP_009316.1"
                     /Dbxref="GeneID:854582"
                     /Dbxref="SGD:S000007273"
                     /Name="NP_009316.1"
                     /Note="Mitochondrial mRNA maturase; forms a complex withNam2p to mediate splicing of the bI4 intron of the COBgene; encoded by both exon and intron sequences ofpartially processed COB mRNA"
                     /experiment="EXISTENCE:direct assay:GO:0000372 Group Iintron splicing [PMID:11142386]"
                     /experiment="EXISTENCE:direct assay:GO:0003723 RNA binding[PMID:11142386]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:2875797]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0000372 Group Iintron splicing [PMID:11142386]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0090615mitochondrial mRNA processing [PMID:8917309]"
                     /gbkey="CDS"
                     /locus_tag="Q0120"
                     /product="intron-encoded RNA maturase bI4"
                     /protein_id="NP_009316.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             40841..42251
                     /label="BI4"
                     /ID="cds-NP_009316.1"
                     /Parent="rna-Q0120"
                     /Dbxref="Genbank:NP_009316.1"
                     /Dbxref="GeneID:854582"
                     /Dbxref="SGD:S000007273"
                     /Name="NP_009316.1"
                     /Note="Mitochondrial mRNA maturase; forms a complex withNam2p to mediate splicing of the bI4 intron of the COBgene; encoded by both exon and intron sequences ofpartially processed COB mRNA"
                     /experiment="EXISTENCE:direct assay:GO:0000372 Group Iintron splicing [PMID:11142386]"
                     /experiment="EXISTENCE:direct assay:GO:0003723 RNA binding[PMID:11142386]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:2875797]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0000372 Group Iintron splicing [PMID:11142386]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0090615mitochondrial mRNA processing [PMID:8917309]"
                     /gbkey="CDS"
                     /locus_tag="Q0120"
                     /product="intron-encoded RNA maturase bI4"
                     /protein_id="NP_009316.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="1"
     gene            36540..40265
                     /label="BI3"
                     /ID="gene-Q0115"
                     /Dbxref="GeneID:854605"
                     /Dbxref="SGD:S000007272"
                     /Name="BI3"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0115"
                     /source="RefSeq"
     mRNA            36540..40265
                     /label="BI3"
                     /ID="rna-Q0115"
                     /Parent="gene-Q0115"
                     /Dbxref="GeneID:854605"
                     /Dbxref="SGD:S000007272"
                     /gbkey="mRNA"
                     /locus_tag="Q0115"
                     /source="RefSeq"
     exon            36540..36954
                     /label="BI3"
                     /ID="exon-Q0115-1"
                     /Parent="rna-Q0115"
                     /Dbxref="GeneID:854605"
                     /Dbxref="SGD:S000007272"
                     /gbkey="mRNA"
                     /locus_tag="Q0115"
                     /source="RefSeq"
     exon            37723..37736
                     /label="BI3"
                     /ID="exon-Q0115-2"
                     /Parent="rna-Q0115"
                     /Dbxref="GeneID:854605"
                     /Dbxref="SGD:S000007272"
                     /gbkey="mRNA"
                     /locus_tag="Q0115"
                     /source="RefSeq"
     exon            39141..40265
                     /label="BI3"
                     /ID="exon-Q0115-3"
                     /Parent="rna-Q0115"
                     /Dbxref="GeneID:854605"
                     /Dbxref="SGD:S000007272"
                     /gbkey="mRNA"
                     /locus_tag="Q0115"
                     /source="RefSeq"
     CDS             36540..36954
                     /label="BI3"
                     /ID="cds-NP_009317.1"
                     /Parent="rna-Q0115"
                     /Dbxref="Genbank:NP_009317.1"
                     /Dbxref="GeneID:854605"
                     /Dbxref="SGD:S000007272"
                     /Name="NP_009317.1"
                     /Note="Mitochondrial mRNA maturase; forms a complex withMrs1p to mediate splicing of the bI3 intron of the COBgene; encoded by both exon and intron sequences ofpartially processed COB mRNA"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:2538624]"
                     /experiment="EXISTENCE:direct assay:GO:0000372 Group Iintron splicing [PMID:11773622]"
                     /experiment="EXISTENCE:direct assay:GO:0003723 RNA binding[PMID:11773622]"
                     /experiment="EXISTENCE:direct assay:GO:0090615mitochondrial mRNA processing [PMID:11773622]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0090615mitochondrial mRNA processing [PMID:2538624]"
                     /gbkey="CDS"
                     /locus_tag="Q0115"
                     /product="cytochrome b mRNA maturase bI3"
                     /protein_id="NP_009317.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             37723..37736
                     /label="BI3"
                     /ID="cds-NP_009317.1"
                     /Parent="rna-Q0115"
                     /Dbxref="Genbank:NP_009317.1"
                     /Dbxref="GeneID:854605"
                     /Dbxref="SGD:S000007272"
                     /Name="NP_009317.1"
                     /Note="Mitochondrial mRNA maturase; forms a complex withMrs1p to mediate splicing of the bI3 intron of the COBgene; encoded by both exon and intron sequences ofpartially processed COB mRNA"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:2538624]"
                     /experiment="EXISTENCE:direct assay:GO:0000372 Group Iintron splicing [PMID:11773622]"
                     /experiment="EXISTENCE:direct assay:GO:0003723 RNA binding[PMID:11773622]"
                     /experiment="EXISTENCE:direct assay:GO:0090615mitochondrial mRNA processing [PMID:11773622]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0090615mitochondrial mRNA processing [PMID:2538624]"
                     /gbkey="CDS"
                     /locus_tag="Q0115"
                     /product="cytochrome b mRNA maturase bI3"
                     /protein_id="NP_009317.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     CDS             39141..40265
                     /label="BI3"
                     /ID="cds-NP_009317.1"
                     /Parent="rna-Q0115"
                     /Dbxref="Genbank:NP_009317.1"
                     /Dbxref="GeneID:854605"
                     /Dbxref="SGD:S000007272"
                     /Name="NP_009317.1"
                     /Note="Mitochondrial mRNA maturase; forms a complex withMrs1p to mediate splicing of the bI3 intron of the COBgene; encoded by both exon and intron sequences ofpartially processed COB mRNA"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:2538624]"
                     /experiment="EXISTENCE:direct assay:GO:0000372 Group Iintron splicing [PMID:11773622]"
                     /experiment="EXISTENCE:direct assay:GO:0003723 RNA binding[PMID:11773622]"
                     /experiment="EXISTENCE:direct assay:GO:0090615mitochondrial mRNA processing [PMID:11773622]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0090615mitochondrial mRNA processing [PMID:2538624]"
                     /gbkey="CDS"
                     /locus_tag="Q0115"
                     /product="cytochrome b mRNA maturase bI3"
                     /protein_id="NP_009317.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     gene            36540..38579
                     /label="BI2"
                     /ID="gene-Q0110"
                     /Dbxref="GeneID:854604"
                     /Dbxref="SGD:S000007271"
                     /Name="BI2"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0110"
                     /source="RefSeq"
     mRNA            36540..38579
                     /label="BI2"
                     /ID="rna-Q0110"
                     /Parent="gene-Q0110"
                     /Dbxref="GeneID:854604"
                     /Dbxref="SGD:S000007271"
                     /gbkey="mRNA"
                     /locus_tag="Q0110"
                     /source="RefSeq"
     exon            36540..36954
                     /label="BI2"
                     /ID="exon-Q0110-1"
                     /Parent="rna-Q0110"
                     /Dbxref="GeneID:854604"
                     /Dbxref="SGD:S000007271"
                     /gbkey="mRNA"
                     /locus_tag="Q0110"
                     /source="RefSeq"
     exon            37723..38579
                     /label="BI2"
                     /ID="exon-Q0110-2"
                     /Parent="rna-Q0110"
                     /Dbxref="GeneID:854604"
                     /Dbxref="SGD:S000007271"
                     /gbkey="mRNA"
                     /locus_tag="Q0110"
                     /source="RefSeq"
     CDS             36540..36954
                     /label="BI2"
                     /ID="cds-NP_009318.1"
                     /Parent="rna-Q0110"
                     /Dbxref="Genbank:NP_009318.1"
                     /Dbxref="GeneID:854604"
                     /Dbxref="SGD:S000007271"
                     /Name="NP_009318.1"
                     /Note="Mitochondrial mRNA maturase with a role in splicing;encoded by both exon and intron sequences of partiallyprocessed COB mRNA"
                     /experiment="EXISTENCE:direct assay:GO:0004518 nucleaseactivity [PMID:8670880]"
                     /experiment="EXISTENCE:direct assay:GO:0008380 RNA splicing[PMID:8670880]"
                     /gbkey="CDS"
                     /locus_tag="Q0110"
                     /product="cytochrome b mRNA maturase bI2"
                     /protein_id="NP_009318.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             37723..38579
                     /label="BI2"
                     /ID="cds-NP_009318.1"
                     /Parent="rna-Q0110"
                     /Dbxref="Genbank:NP_009318.1"
                     /Dbxref="GeneID:854604"
                     /Dbxref="SGD:S000007271"
                     /Name="NP_009318.1"
                     /Note="Mitochondrial mRNA maturase with a role in splicing;encoded by both exon and intron sequences of partiallyprocessed COB mRNA"
                     /experiment="EXISTENCE:direct assay:GO:0004518 nucleaseactivity [PMID:8670880]"
                     /experiment="EXISTENCE:direct assay:GO:0008380 RNA splicing[PMID:8670880]"
                     /gbkey="CDS"
                     /locus_tag="Q0110"
                     /product="cytochrome b mRNA maturase bI2"
                     /protein_id="NP_009318.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="2"
     rep_origin      45227..47927
                     /label="Untitled Feature"
                     /ID="id-NC_001224.1:45227..47927"
                     /Dbxref="SGD:S000029672"
                     /Note="ORI6; Mitochondrial origin of replication"
                     /gbkey="rep_origin"
                     /source="RefSeq"
     gene            46723..46953
                     /label="OLI1"
                     /ID="gene-Q0130"
                     /Dbxref="GeneID:854584"
                     /Dbxref="SGD:S000007274"
                     /Name="OLI1"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /gene_synonym="ATP9"
                     /gene_synonym="OLI3"
                     /locus_tag="Q0130"
                     /source="RefSeq"
     mRNA            46723..46953
                     /label="OLI1"
                     /ID="rna-Q0130"
                     /Parent="gene-Q0130"
                     /Dbxref="GeneID:854584"
                     /Dbxref="SGD:S000007274"
                     /gbkey="mRNA"
                     /locus_tag="Q0130"
                     /source="RefSeq"
     exon            46723..46953
                     /label="OLI1"
                     /ID="exon-Q0130-1"
                     /Parent="rna-Q0130"
                     /Dbxref="GeneID:854584"
                     /Dbxref="SGD:S000007274"
                     /gbkey="mRNA"
                     /locus_tag="Q0130"
                     /source="RefSeq"
     CDS             46723..46953
                     /label="OLI1"
                     /ID="cds-NP_009319.1"
                     /Parent="rna-Q0130"
                     /Dbxref="Genbank:NP_009319.1"
                     /Dbxref="GeneID:854584"
                     /Dbxref="SGD:S000007274"
                     /Name="NP_009319.1"
                     /Note="F0-ATP synthase subunit c (ATPase-associatedproteolipid); encoded on the mitochondrial genome; mutationconfers oligomycin resistance; expression is specificallydependent on the nuclear genes AEP1 and AEP2"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0015986 ATPsynthesis coupled proton transport [PMID:20691145]"
                     /experiment="EXISTENCE:direct assay:GO:0016887 ATPaseactivity [PMID:18722382]"
                     /experiment="EXISTENCE:direct assay:GO:0046933proton-transporting ATP synthase activity, rotationalmechanism [PMID:20691145]"
                     /experiment="EXISTENCE:physical interaction:GO:0000276mitochondrial proton-transporting ATP synthase complex,coupling factor F(o) [PMID:2894987]"
                     /gbkey="CDS"
                     /locus_tag="Q0130"
                     /product="F0 ATP synthase subunit c"
                     /protein_id="NP_009319.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     gene            48201..48290
                     /label="tS(UGA)Q2"
                     /ID="gene-tS(UGA)Q2"
                     /Dbxref="GeneID:854585"
                     /Dbxref="SGD:S000007333"
                     /Name="tS(UGA)Q2"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tS(UGA)Q2"
                     /source="RefSeq"
     tRNA            48201..48290
                     /label="tS(UGA)Q2"
                     /ID="rna-tS(UGA)Q2"
                     /Parent="gene-tS(UGA)Q2"
                     /Dbxref="GeneID:854585"
                     /Dbxref="SGD:S000007333"
                     /Note="Mitochondrial serine tRNA (tRNA-Ser); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tS(UGA)Q2"
                     /product="tRNA-Ser"
                     /source="RefSeq"
     exon            48201..48290
                     /label="tS(UGA)Q2"
                     /ID="exon-tS(UGA)Q2-1"
                     /Parent="rna-tS(UGA)Q2"
                     /Dbxref="GeneID:854585"
                     /Dbxref="SGD:S000007333"
                     /Note="Mitochondrial serine tRNA (tRNA-Ser); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tS(UGA)Q2"
                     /product="tRNA-Ser"
                     /source="RefSeq"
     gene            48901..50097
                     /label="VAR1"
                     /ID="gene-Q0140"
                     /Dbxref="GeneID:854586"
                     /Dbxref="SGD:S000007275"
                     /Name="VAR1"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /gene_synonym="uS3m"
                     /locus_tag="Q0140"
                     /source="RefSeq"
     mRNA            48901..50097
                     /label="VAR1"
                     /ID="rna-Q0140"
                     /Parent="gene-Q0140"
                     /Dbxref="GeneID:854586"
                     /Dbxref="SGD:S000007275"
                     /gbkey="mRNA"
                     /locus_tag="Q0140"
                     /source="RefSeq"
     exon            48901..50097
                     /label="VAR1"
                     /ID="exon-Q0140-1"
                     /Parent="rna-Q0140"
                     /Dbxref="GeneID:854586"
                     /Dbxref="SGD:S000007275"
                     /gbkey="mRNA"
                     /locus_tag="Q0140"
                     /source="RefSeq"
     CDS             48901..50097
                     /label="VAR1"
                     /ID="cds-NP_009320.1"
                     /Parent="rna-Q0140"
                     /Dbxref="Genbank:NP_009320.1"
                     /Dbxref="GeneID:854586"
                     /Dbxref="SGD:S000007275"
                     /Name="NP_009320.1"
                     /Note="Mitochondrial ribosomal protein of the smallsubunit; mitochondrially-encoded; polymorphic in differentstrains due to variation in number of AAT (asparagine)codons; translated near the mitochondrial inner membrane;may have a role in loss of mitochondrial DNA under stressconditions"
                     /experiment="EXISTENCE:direct assay:GO:0003735 structuralconstituent of ribosome [PMID:11278769]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961|PMID:24769239]"
                     /experiment="EXISTENCE:direct assay:GO:0005761mitochondrial ribosome [PMID:387793]"
                     /experiment="EXISTENCE:direct assay:GO:0005763mitochondrial small ribosomal subunit [PMID:11278769]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0032543mitochondrial translation [PMID:24186543|PMID:6250484]"
                     /gbkey="CDS"
                     /locus_tag="Q0140"
                     /product="mitochondrial 37S ribosomal protein VAR1"
                     /protein_id="NP_009320.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     rep_origin      complement(54567..54840)
                     /label="Untitled Feature"
                     /ID="id-NC_001224.1:54567..54840"
                     /Dbxref="SGD:S000029669"
                     /Note="ORI3; Mitochondrial origin of replication"
                     /gbkey="rep_origin"
                     /source="RefSeq"
     rep_origin      complement(56567..56832)
                     /label="Untitled Feature"
                     /ID="id-NC_001224.1:56567..56832"
                     /Dbxref="SGD:S000029670"
                     /Note="ORI4; Mitochondrial origin of replication"
                     /gbkey="rep_origin"
                     /source="RefSeq"
     gene            58009..62447
                     /label="21S_RRNA"
                     /ID="gene-Q0158"
                     /Dbxref="GeneID:9164988"
                     /Dbxref="SGD:S000007288"
                     /Name="21S_RRNA"
                     /gbkey="Gene"
                     /gene_biotype="rRNA"
                     /locus_tag="Q0158"
                     /source="RefSeq"
     rRNA            58009..62447
                     /label="21S_RRNA"
                     /ID="rna-Q0158"
                     /Parent="gene-Q0158"
                     /Dbxref="GeneID:9164988"
                     /Dbxref="SGD:S000007288"
                     /Note="Mitochondrial 21S rRNA; intron encodes the I-SceIDNA endonuclease"
                     /experiment="EXISTENCE:direct assay:GO:0005762mitochondrial large ribosomal subunit [PMID:6759872]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0003735structural constituent of ribosome [PMID:6759872]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0032543mitochondrial translation [PMID:6759872]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0042255 ribosomeassembly [PMID:6759872]"
                     /gbkey="rRNA"
                     /locus_tag="Q0158"
                     /product="21S ribosomal RNA"
                     /source="RefSeq"
     exon            58009..62447
                     /label="21S_RRNA"
                     /ID="exon-Q0158-1"
                     /Parent="rna-Q0158"
                     /Dbxref="GeneID:9164988"
                     /Dbxref="SGD:S000007288"
                     /Note="Mitochondrial 21S rRNA; intron encodes the I-SceIDNA endonuclease"
                     /experiment="EXISTENCE:direct assay:GO:0005762mitochondrial large ribosomal subunit [PMID:6759872]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0003735structural constituent of ribosome [PMID:6759872]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0032543mitochondrial translation [PMID:6759872]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0042255 ribosomeassembly [PMID:6759872]"
                     /gbkey="rRNA"
                     /locus_tag="Q0158"
                     /product="21S ribosomal RNA"
                     /source="RefSeq"
     gene            61022..61729
                     /label="SCEI"
                     /ID="gene-Q0160"
                     /Dbxref="GeneID:854590"
                     /Dbxref="SGD:S000007279"
                     /Name="SCEI"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0160"
                     /source="RefSeq"
     mRNA            61022..61729
                     /label="SCEI"
                     /ID="rna-Q0160"
                     /Parent="gene-Q0160"
                     /Dbxref="GeneID:854590"
                     /Dbxref="SGD:S000007279"
                     /gbkey="mRNA"
                     /locus_tag="Q0160"
                     /source="RefSeq"
     exon            61022..61729
                     /label="SCEI"
                     /ID="exon-Q0160-1"
                     /Parent="rna-Q0160"
                     /Dbxref="GeneID:854590"
                     /Dbxref="SGD:S000007279"
                     /gbkey="mRNA"
                     /locus_tag="Q0160"
                     /source="RefSeq"
     CDS             61022..61729
                     /label="SCEI"
                     /ID="cds-NP_009324.1"
                     /Parent="rna-Q0160"
                     /Dbxref="Genbank:NP_009324.1"
                     /Dbxref="GeneID:854590"
                     /Dbxref="SGD:S000007279"
                     /Name="NP_009324.1"
                     /Note="I-SceI DNA endonuclease; encoded by themitochondrial group I intron of the 21S_rRNA gene; mediatesgene conversion that propagates the intron into intron-lesscopies of the 21S_rRNA gene"
                     /experiment="EXISTENCE:direct assay:GO:0004519 endonucleaseactivity [PMID:16478204|PMID:8335007]"
                     /gbkey="CDS"
                     /locus_tag="Q0160"
                     /product="intron-encoded endonuclease I-SceI"
                     /protein_id="NP_009324.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     gene            63862..63937
                     /label="tT(UGU)Q1"
                     /ID="gene-tT(UGU)Q1"
                     /Dbxref="GeneID:854591"
                     /Dbxref="SGD:S000007334"
                     /Name="tT(UGU)Q1"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tT(UGU)Q1"
                     /source="RefSeq"
     tRNA            63862..63937
                     /label="tT(UGU)Q1"
                     /ID="rna-tT(UGU)Q1"
                     /Parent="gene-tT(UGU)Q1"
                     /Dbxref="GeneID:854591"
                     /Dbxref="SGD:S000007334"
                     /Note="Mitochondrial threonine tRNA (tRNA-Thr); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:375006]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:375006]"
                     /experiment="EXISTENCE:direct assay:GO:0030533 tripletcodon-amino acid adaptor activity [PMID:375006]"
                     /gbkey="tRNA"
                     /locus_tag="tT(UGU)Q1"
                     /product="tRNA-Thr"
                     /source="RefSeq"
     exon            63862..63937
                     /label="tT(UGU)Q1"
                     /ID="exon-tT(UGU)Q1-1"
                     /Parent="rna-tT(UGU)Q1"
                     /Dbxref="GeneID:854591"
                     /Dbxref="SGD:S000007334"
                     /Note="Mitochondrial threonine tRNA (tRNA-Thr); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:375006]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:375006]"
                     /experiment="EXISTENCE:direct assay:GO:0030533 tripletcodon-amino acid adaptor activity [PMID:375006]"
                     /gbkey="tRNA"
                     /locus_tag="tT(UGU)Q1"
                     /product="tRNA-Thr"
                     /source="RefSeq"
     gene            64415..64490
                     /label="tC(GCA)Q"
                     /ID="gene-tC(GCA)Q"
                     /Dbxref="GeneID:854606"
                     /Dbxref="SGD:S000007316"
                     /Name="tC(GCA)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tC(GCA)Q"
                     /source="RefSeq"
     tRNA            64415..64490
                     /label="tC(GCA)Q"
                     /ID="rna-tC(GCA)Q"
                     /Parent="gene-tC(GCA)Q"
                     /Dbxref="GeneID:854606"
                     /Dbxref="SGD:S000007316"
                     /Note="Mitochondrial cysteine tRNA (tRNA-Cys)"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tC(GCA)Q"
                     /product="tRNA-Cys"
                     /source="RefSeq"
     exon            64415..64490
                     /label="tC(GCA)Q"
                     /ID="exon-tC(GCA)Q-1"
                     /Parent="rna-tC(GCA)Q"
                     /Dbxref="GeneID:854606"
                     /Dbxref="SGD:S000007316"
                     /Note="Mitochondrial cysteine tRNA (tRNA-Cys)"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tC(GCA)Q"
                     /product="tRNA-Cys"
                     /source="RefSeq"
     gene            64596..64670
                     /label="tH(GUG)Q"
                     /ID="gene-tH(GUG)Q"
                     /Dbxref="GeneID:854607"
                     /Dbxref="SGD:S000007321"
                     /Name="tH(GUG)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tH(GUG)Q"
                     /source="RefSeq"
     tRNA            64596..64670
                     /label="tH(GUG)Q"
                     /ID="rna-tH(GUG)Q"
                     /Parent="gene-tH(GUG)Q"
                     /Dbxref="GeneID:854607"
                     /Dbxref="SGD:S000007321"
                     /Note="Mitochondrial histidine tRNA (tRNA-His); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:16777356]"
                     /experiment="EXISTENCE:direct assay:GO:0030371 translationrepressor activity [PMID:27609601]"
                     /experiment="EXISTENCE:direct assay:GO:0043022 ribosomebinding [PMID:27609601]"
                     /experiment="EXISTENCE:direct assay:GO:0043555 regulationof translation in response to stress [PMID:27609601]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0032543mitochondrial translation [PMID:16777356]"
                     /gbkey="tRNA"
                     /locus_tag="tH(GUG)Q"
                     /product="tRNA-His"
                     /source="RefSeq"
     exon            64596..64670
                     /label="tH(GUG)Q"
                     /ID="exon-tH(GUG)Q-1"
                     /Parent="rna-tH(GUG)Q"
                     /Dbxref="GeneID:854607"
                     /Dbxref="SGD:S000007321"
                     /Note="Mitochondrial histidine tRNA (tRNA-His); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:16777356]"
                     /experiment="EXISTENCE:direct assay:GO:0030371 translationrepressor activity [PMID:27609601]"
                     /experiment="EXISTENCE:direct assay:GO:0043022 ribosomebinding [PMID:27609601]"
                     /experiment="EXISTENCE:direct assay:GO:0043555 regulationof translation in response to stress [PMID:27609601]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0032543mitochondrial translation [PMID:16777356]"
                     /gbkey="tRNA"
                     /locus_tag="tH(GUG)Q"
                     /product="tRNA-His"
                     /source="RefSeq"
     gene            66095..66179
                     /label="tL(UAA)Q"
                     /ID="gene-tL(UAA)Q"
                     /Dbxref="GeneID:854609"
                     /Dbxref="SGD:S000007324"
                     /Name="tL(UAA)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tL(UAA)Q"
                     /source="RefSeq"
     tRNA            66095..66179
                     /label="tL(UAA)Q"
                     /ID="rna-tL(UAA)Q"
                     /Parent="gene-tL(UAA)Q"
                     /Dbxref="GeneID:854609"
                     /Dbxref="SGD:S000007324"
                     /Note="Mitochondrial leucine tRNA (tRNA-Leu); predicted bytRNAscan-SE analysis; required for mitochondrialtranslation and indirectly for maintenance of themitochondrial genome; mutations in the orthologous humangene cause the neurological disease MELAS"
                     /experiment="EXISTENCE:direct assay:GO:0030371 translationrepressor activity [PMID:27609601]"
                     /experiment="EXISTENCE:direct assay:GO:0043022 ribosomebinding [PMID:27609601]"
                     /experiment="EXISTENCE:direct assay:GO:0043555 regulationof translation in response to stress [PMID:27609601]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0005739mitochondrion [PMID:9872396]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006414translational elongation [PMID:9872396]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0032543mitochondrial translation [PMID:16777356]"
                     /gbkey="tRNA"
                     /locus_tag="tL(UAA)Q"
                     /product="tRNA-Leu"
                     /source="RefSeq"
     exon            66095..66179
                     /label="tL(UAA)Q"
                     /ID="exon-tL(UAA)Q-1"
                     /Parent="rna-tL(UAA)Q"
                     /Dbxref="GeneID:854609"
                     /Dbxref="SGD:S000007324"
                     /Note="Mitochondrial leucine tRNA (tRNA-Leu); predicted bytRNAscan-SE analysis; required for mitochondrialtranslation and indirectly for maintenance of themitochondrial genome; mutations in the orthologous humangene cause the neurological disease MELAS"
                     /experiment="EXISTENCE:direct assay:GO:0030371 translationrepressor activity [PMID:27609601]"
                     /experiment="EXISTENCE:direct assay:GO:0043022 ribosomebinding [PMID:27609601]"
                     /experiment="EXISTENCE:direct assay:GO:0043555 regulationof translation in response to stress [PMID:27609601]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0005739mitochondrion [PMID:9872396]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0006414translational elongation [PMID:9872396]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0032543mitochondrial translation [PMID:16777356]"
                     /gbkey="tRNA"
                     /locus_tag="tL(UAA)Q"
                     /product="tRNA-Leu"
                     /source="RefSeq"
     gene            66210..66285
                     /label="tQ(UUG)Q"
                     /ID="gene-tQ(UUG)Q"
                     /Dbxref="GeneID:854610"
                     /Dbxref="SGD:S000007329"
                     /Name="tQ(UUG)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tQ(UUG)Q"
                     /source="RefSeq"
     tRNA            66210..66285
                     /label="tQ(UUG)Q"
                     /ID="rna-tQ(UUG)Q"
                     /Parent="gene-tQ(UUG)Q"
                     /Dbxref="GeneID:854610"
                     /Dbxref="SGD:S000007329"
                     /Note="Mitochondrial glutamine tRNA (tRNA-Gln); predictedby tRNAscan-SE analysis; thiolation of uridine at wobbleposition (34) requires Ncs6p"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tQ(UUG)Q"
                     /product="tRNA-Gln"
                     /source="RefSeq"
     exon            66210..66285
                     /label="tQ(UUG)Q"
                     /ID="exon-tQ(UUG)Q-1"
                     /Parent="rna-tQ(UUG)Q"
                     /Dbxref="GeneID:854610"
                     /Dbxref="SGD:S000007329"
                     /Note="Mitochondrial glutamine tRNA (tRNA-Gln); predictedby tRNAscan-SE analysis; thiolation of uridine at wobbleposition (34) requires Ncs6p"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tQ(UUG)Q"
                     /product="tRNA-Gln"
                     /source="RefSeq"
     gene            67061..67134
                     /label="tK(UUU)Q"
                     /ID="gene-tK(UUU)Q"
                     /Dbxref="GeneID:854611"
                     /Dbxref="SGD:S000007323"
                     /Name="tK(UUU)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tK(UUU)Q"
                     /source="RefSeq"
     tRNA            67061..67134
                     /label="tK(UUU)Q"
                     /ID="rna-tK(UUU)Q"
                     /Parent="gene-tK(UUU)Q"
                     /Dbxref="GeneID:854611"
                     /Dbxref="SGD:S000007323"
                     /Note="Mitochondrial lysine tRNA (tRNA-Lys); predicted bytRNAscan-SE analysis; modification of wobble base U34 isdecreased at elevated temperatures, decreasing ability todecode AAG codons; this function is performed at hightemperature by imported cytoplasmic tRNA-Lys with CUUanticodon"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:387075]"
                     /gbkey="tRNA"
                     /locus_tag="tK(UUU)Q"
                     /product="tRNA-Lys"
                     /source="RefSeq"
     exon            67061..67134
                     /label="tK(UUU)Q"
                     /ID="exon-tK(UUU)Q-1"
                     /Parent="rna-tK(UUU)Q"
                     /Dbxref="GeneID:854611"
                     /Dbxref="SGD:S000007323"
                     /Note="Mitochondrial lysine tRNA (tRNA-Lys); predicted bytRNAscan-SE analysis; modification of wobble base U34 isdecreased at elevated temperatures, decreasing ability todecode AAG codons; this function is performed at hightemperature by imported cytoplasmic tRNA-Lys with CUUanticodon"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:387075]"
                     /gbkey="tRNA"
                     /locus_tag="tK(UUU)Q"
                     /product="tRNA-Lys"
                     /source="RefSeq"
     gene            67309..67381
                     /label="tR(UCU)Q1"
                     /ID="gene-tR(UCU)Q1"
                     /Dbxref="GeneID:854612"
                     /Dbxref="SGD:S000007331"
                     /Name="tR(UCU)Q1"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tR(UCU)Q1"
                     /source="RefSeq"
     tRNA            67309..67381
                     /label="tR(UCU)Q1"
                     /ID="rna-tR(UCU)Q1"
                     /Parent="gene-tR(UCU)Q1"
                     /Dbxref="GeneID:854612"
                     /Dbxref="SGD:S000007331"
                     /Note="Mitochondrial arginine tRNA (tRNA-Arg); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tR(UCU)Q1"
                     /product="tRNA-Arg"
                     /source="RefSeq"
     exon            67309..67381
                     /label="tR(UCU)Q1"
                     /ID="exon-tR(UCU)Q1-1"
                     /Parent="rna-tR(UCU)Q1"
                     /Dbxref="GeneID:854612"
                     /Dbxref="SGD:S000007331"
                     /Note="Mitochondrial arginine tRNA (tRNA-Arg); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tR(UCU)Q1"
                     /product="tRNA-Arg"
                     /source="RefSeq"
     gene            67468..67542
                     /label="tG(UCC)Q"
                     /ID="gene-tG(UCC)Q"
                     /Dbxref="GeneID:854613"
                     /Dbxref="SGD:S000007320"
                     /Name="tG(UCC)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tG(UCC)Q"
                     /source="RefSeq"
     tRNA            67468..67542
                     /label="tG(UCC)Q"
                     /ID="rna-tG(UCC)Q"
                     /Parent="gene-tG(UCC)Q"
                     /Dbxref="GeneID:854613"
                     /Dbxref="SGD:S000007320"
                     /Note="Mitochondrial glycine tRNA (tRNA-Gly); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tG(UCC)Q"
                     /product="tRNA-Gly"
                     /source="RefSeq"
     exon            67468..67542
                     /label="tG(UCC)Q"
                     /ID="exon-tG(UCC)Q-1"
                     /Parent="rna-tG(UCC)Q"
                     /Dbxref="GeneID:854613"
                     /Dbxref="SGD:S000007320"
                     /Note="Mitochondrial glycine tRNA (tRNA-Gly); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tG(UCC)Q"
                     /product="tRNA-Gly"
                     /source="RefSeq"
     gene            68322..68396
                     /label="tD(GUC)Q"
                     /ID="gene-tD(GUC)Q"
                     /Dbxref="GeneID:854614"
                     /Dbxref="SGD:S000007317"
                     /Name="tD(GUC)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tD(GUC)Q"
                     /source="RefSeq"
     tRNA            68322..68396
                     /label="tD(GUC)Q"
                     /ID="rna-tD(GUC)Q"
                     /Parent="gene-tD(GUC)Q"
                     /Dbxref="GeneID:854614"
                     /Dbxref="SGD:S000007317"
                     /Note="Mitochondrial aspartate tRNA (tRNA-Asp); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:mutant phenotype:GO:0005739mitochondrion [PMID:7024270]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0032543mitochondrial translation [PMID:7024270]"
                     /gbkey="tRNA"
                     /locus_tag="tD(GUC)Q"
                     /product="tRNA-Asp"
                     /source="RefSeq"
     exon            68322..68396
                     /label="tD(GUC)Q"
                     /ID="exon-tD(GUC)Q-1"
                     /Parent="rna-tD(GUC)Q"
                     /Dbxref="GeneID:854614"
                     /Dbxref="SGD:S000007317"
                     /Note="Mitochondrial aspartate tRNA (tRNA-Asp); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:mutant phenotype:GO:0005739mitochondrion [PMID:7024270]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0032543mitochondrial translation [PMID:7024270]"
                     /gbkey="tRNA"
                     /locus_tag="tD(GUC)Q"
                     /product="tRNA-Asp"
                     /source="RefSeq"
     gene            69203..69288
                     /label="tS(GCU)Q1"
                     /ID="gene-tS(GCU)Q1"
                     /Dbxref="GeneID:854615"
                     /Dbxref="SGD:S000007332"
                     /Name="tS(GCU)Q1"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tS(GCU)Q1"
                     /source="RefSeq"
     tRNA            69203..69288
                     /label="tS(GCU)Q1"
                     /ID="rna-tS(GCU)Q1"
                     /Parent="gene-tS(GCU)Q1"
                     /Dbxref="GeneID:854615"
                     /Dbxref="SGD:S000007332"
                     /Note="Mitochondrial serine tRNA (tRNA-Ser); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tS(GCU)Q1"
                     /product="tRNA-Ser"
                     /source="RefSeq"
     exon            69203..69288
                     /label="tS(GCU)Q1"
                     /ID="exon-tS(GCU)Q1-1"
                     /Parent="rna-tS(GCU)Q1"
                     /Dbxref="GeneID:854615"
                     /Dbxref="SGD:S000007332"
                     /Note="Mitochondrial serine tRNA (tRNA-Ser); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tS(GCU)Q1"
                     /product="tRNA-Ser"
                     /source="RefSeq"
     gene            69289..69362
                     /label="tR(ACG)Q2"
                     /ID="gene-tR(ACG)Q2"
                     /Dbxref="GeneID:854616"
                     /Dbxref="SGD:S000007330"
                     /Name="tR(ACG)Q2"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tR(ACG)Q2"
                     /source="RefSeq"
     tRNA            69289..69362
                     /label="tR(ACG)Q2"
                     /ID="rna-tR(ACG)Q2"
                     /Parent="gene-tR(ACG)Q2"
                     /Dbxref="GeneID:854616"
                     /Dbxref="SGD:S000007330"
                     /Note="Mitochondrial arginine tRNA (tRNA-Arg); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tR(ACG)Q2"
                     /product="tRNA-Arg"
                     /source="RefSeq"
     exon            69289..69362
                     /label="tR(ACG)Q2"
                     /ID="exon-tR(ACG)Q2-1"
                     /Parent="rna-tR(ACG)Q2"
                     /Dbxref="GeneID:854616"
                     /Dbxref="SGD:S000007330"
                     /Note="Mitochondrial arginine tRNA (tRNA-Arg); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tR(ACG)Q2"
                     /product="tRNA-Arg"
                     /source="RefSeq"
     gene            69846..69921
                     /label="tA(UGC)Q"
                     /ID="gene-tA(UGC)Q"
                     /Dbxref="GeneID:854617"
                     /Dbxref="SGD:S000007315"
                     /Name="tA(UGC)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tA(UGC)Q"
                     /source="RefSeq"
     tRNA            69846..69921
                     /label="tA(UGC)Q"
                     /ID="rna-tA(UGC)Q"
                     /Parent="gene-tA(UGC)Q"
                     /Dbxref="GeneID:854617"
                     /Dbxref="SGD:S000007315"
                     /Note="Mitochondrial alanine tRNA (tRNA-Ala); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tA(UGC)Q"
                     /product="tRNA-Ala"
                     /source="RefSeq"
     exon            69846..69921
                     /label="tA(UGC)Q"
                     /ID="exon-tA(UGC)Q-1"
                     /Parent="rna-tA(UGC)Q"
                     /Dbxref="GeneID:854617"
                     /Dbxref="SGD:S000007315"
                     /Note="Mitochondrial alanine tRNA (tRNA-Ala); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tA(UGC)Q"
                     /product="tRNA-Ala"
                     /source="RefSeq"
     gene            70162..70237
                     /label="tI(GAU)Q"
                     /ID="gene-tI(GAU)Q"
                     /Dbxref="GeneID:854618"
                     /Dbxref="SGD:S000007322"
                     /Name="tI(GAU)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tI(GAU)Q"
                     /source="RefSeq"
     tRNA            70162..70237
                     /label="tI(GAU)Q"
                     /ID="rna-tI(GAU)Q"
                     /Parent="gene-tI(GAU)Q"
                     /Dbxref="GeneID:854618"
                     /Dbxref="SGD:S000007322"
                     /Note="Mitochondrial isoleucine tRNA (tRNA-Ile); predictedby tRNAscan-SE analysis; mutations in human ortholog arelinked to conditions such as progressive encephalopathy,metabolic syndrome, and hypomagnesaemia"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tI(GAU)Q"
                     /product="tRNA-Ile"
                     /source="RefSeq"
     exon            70162..70237
                     /label="tI(GAU)Q"
                     /ID="exon-tI(GAU)Q-1"
                     /Parent="rna-tI(GAU)Q"
                     /Dbxref="GeneID:854618"
                     /Dbxref="SGD:S000007322"
                     /Note="Mitochondrial isoleucine tRNA (tRNA-Ile); predictedby tRNAscan-SE analysis; mutations in human ortholog arelinked to conditions such as progressive encephalopathy,metabolic syndrome, and hypomagnesaemia"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tI(GAU)Q"
                     /product="tRNA-Ile"
                     /source="RefSeq"
     gene            70824..70907
                     /label="tY(GUA)Q"
                     /ID="gene-tY(GUA)Q"
                     /Dbxref="GeneID:854619"
                     /Dbxref="SGD:S000007338"
                     /Name="tY(GUA)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tY(GUA)Q"
                     /source="RefSeq"
     tRNA            70824..70907
                     /label="tY(GUA)Q"
                     /ID="rna-tY(GUA)Q"
                     /Parent="gene-tY(GUA)Q"
                     /Dbxref="GeneID:854619"
                     /Dbxref="SGD:S000007338"
                     /Note="Mitochondrial tyrosine tRNA (tRNA-Tyr)"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tY(GUA)Q"
                     /product="tRNA-Tyr"
                     /source="RefSeq"
     exon            70824..70907
                     /label="tY(GUA)Q"
                     /ID="exon-tY(GUA)Q-1"
                     /Parent="rna-tY(GUA)Q"
                     /Dbxref="GeneID:854619"
                     /Dbxref="SGD:S000007338"
                     /Note="Mitochondrial tyrosine tRNA (tRNA-Tyr)"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tY(GUA)Q"
                     /product="tRNA-Tyr"
                     /source="RefSeq"
     gene            71433..71503
                     /label="tN(GUU)Q"
                     /ID="gene-tN(GUU)Q"
                     /Dbxref="GeneID:854620"
                     /Dbxref="SGD:S000007327"
                     /Name="tN(GUU)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tN(GUU)Q"
                     /source="RefSeq"
     tRNA            71433..71503
                     /label="tN(GUU)Q"
                     /ID="rna-tN(GUU)Q"
                     /Parent="gene-tN(GUU)Q"
                     /Dbxref="GeneID:854620"
                     /Dbxref="SGD:S000007327"
                     /Note="Mitochondrial asparagine tRNA (tRNA-Asn); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tN(GUU)Q"
                     /product="tRNA-Asn"
                     /source="RefSeq"
     exon            71433..71503
                     /label="tN(GUU)Q"
                     /ID="exon-tN(GUU)Q-1"
                     /Parent="rna-tN(GUU)Q"
                     /Dbxref="GeneID:854620"
                     /Dbxref="SGD:S000007327"
                     /Note="Mitochondrial asparagine tRNA (tRNA-Asn); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tN(GUU)Q"
                     /product="tRNA-Asn"
                     /source="RefSeq"
     gene            72630..72705
                     /label="tM(CAU)Q1"
                     /ID="gene-tM(CAU)Q1"
                     /Dbxref="GeneID:854621"
                     /Dbxref="SGD:S000007325"
                     /Name="tM(CAU)Q1"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tM(CAU)Q1"
                     /source="RefSeq"
     tRNA            72630..72705
                     /label="tM(CAU)Q1"
                     /ID="rna-tM(CAU)Q1"
                     /Parent="gene-tM(CAU)Q1"
                     /Dbxref="GeneID:854621"
                     /Dbxref="SGD:S000007325"
                     /Note="Mitochondrial methionine tRNA (tRNA-Met); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tM(CAU)Q1"
                     /product="tRNA-Met"
                     /source="RefSeq"
     exon            72630..72705
                     /label="tM(CAU)Q1"
                     /ID="exon-tM(CAU)Q1-1"
                     /Parent="rna-tM(CAU)Q1"
                     /Dbxref="GeneID:854621"
                     /Dbxref="SGD:S000007325"
                     /Note="Mitochondrial methionine tRNA (tRNA-Met); predictedby tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tM(CAU)Q1"
                     /product="tRNA-Met"
                     /source="RefSeq"
     gene            73758..74513
                     /label="COX2"
                     /ID="gene-Q0250"
                     /Dbxref="GeneID:854622"
                     /Dbxref="SGD:S000007281"
                     /Name="COX2"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /gene_synonym="OXI1"
                     /gene_synonym="OXII"
                     /locus_tag="Q0250"
                     /source="RefSeq"
     mRNA            73758..74513
                     /label="COX2"
                     /ID="rna-Q0250"
                     /Parent="gene-Q0250"
                     /Dbxref="GeneID:854622"
                     /Dbxref="SGD:S000007281"
                     /gbkey="mRNA"
                     /locus_tag="Q0250"
                     /source="RefSeq"
     exon            73758..74513
                     /label="COX2"
                     /ID="exon-Q0250-1"
                     /Parent="rna-Q0250"
                     /Dbxref="GeneID:854622"
                     /Dbxref="SGD:S000007281"
                     /gbkey="mRNA"
                     /locus_tag="Q0250"
                     /source="RefSeq"
     CDS             73758..74513
                     /label="COX2"
                     /ID="cds-NP_009326.1"
                     /Parent="rna-Q0250"
                     /Dbxref="Genbank:NP_009326.1"
                     /Dbxref="GeneID:854622"
                     /Dbxref="SGD:S000007281"
                     /Name="NP_009326.1"
                     /Note="Subunit II of cytochrome c oxidase (Complex IV);Complex IV is the terminal member of the mitochondrialinner membrane electron transport chain; one of threemitochondrially-encoded subunits"
                     /experiment="EXISTENCE:direct assay:GO:0004129 cytochrome-coxidase activity [PMID:1331058|PMID:7851399]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:16823961|PMID:24769239]"
                     /experiment="EXISTENCE:direct assay:GO:0005751mitochondrial respiratory chain complex IV [PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0006123mitochondrial electron transport, cytochrome c to oxygen[PMID:1331058]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:224191]"
                     /gbkey="CDS"
                     /locus_tag="Q0250"
                     /product="cytochrome c oxidase subunit 2"
                     /protein_id="NP_009326.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     gene            74495..75984
                     /label="Q0255"
                     /ID="gene-Q0255"
                     /Dbxref="GeneID:854623"
                     /Dbxref="SGD:S000007282"
                     /Name="Q0255"
                     /Note="ORF1"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /locus_tag="Q0255"
                     /source="RefSeq"
     mRNA            74495..75984
                     /label="Q0255"
                     /ID="rna-Q0255"
                     /Parent="gene-Q0255"
                     /Dbxref="GeneID:854623"
                     /Dbxref="SGD:S000007282"
                     /gbkey="mRNA"
                     /locus_tag="Q0255"
                     /source="RefSeq"
     exon            74495..75622
                     /label="Q0255"
                     /ID="exon-Q0255-1"
                     /Parent="rna-Q0255"
                     /Dbxref="GeneID:854623"
                     /Dbxref="SGD:S000007282"
                     /gbkey="mRNA"
                     /locus_tag="Q0255"
                     /source="RefSeq"
     exon            75663..75872
                     /label="Q0255"
                     /ID="exon-Q0255-2"
                     /Parent="rna-Q0255"
                     /Dbxref="GeneID:854623"
                     /Dbxref="SGD:S000007282"
                     /gbkey="mRNA"
                     /locus_tag="Q0255"
                     /source="RefSeq"
     exon            75904..75984
                     /label="Q0255"
                     /ID="exon-Q0255-3"
                     /Parent="rna-Q0255"
                     /Dbxref="GeneID:854623"
                     /Dbxref="SGD:S000007282"
                     /gbkey="mRNA"
                     /locus_tag="Q0255"
                     /source="RefSeq"
     CDS             74495..75622
                     /label="Q0255"
                     /ID="cds-NP_009327.1"
                     /Parent="rna-Q0255"
                     /Dbxref="Genbank:NP_009327.1"
                     /Dbxref="GeneID:854623"
                     /Dbxref="SGD:S000007282"
                     /Name="NP_009327.1"
                     /Note="Maturase-like protein"
                     /gbkey="CDS"
                     /locus_tag="Q0255"
                     /product="maturase-like protein"
                     /protein_id="NP_009327.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             75663..75872
                     /label="Q0255"
                     /ID="cds-NP_009327.1"
                     /Parent="rna-Q0255"
                     /Dbxref="Genbank:NP_009327.1"
                     /Dbxref="GeneID:854623"
                     /Dbxref="SGD:S000007282"
                     /Name="NP_009327.1"
                     /Note="Maturase-like protein"
                     /gbkey="CDS"
                     /locus_tag="Q0255"
                     /product="maturase-like protein"
                     /protein_id="NP_009327.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     CDS             75904..75984
                     /label="Q0255"
                     /ID="cds-NP_009327.1"
                     /Parent="rna-Q0255"
                     /Dbxref="Genbank:NP_009327.1"
                     /Dbxref="GeneID:854623"
                     /Dbxref="SGD:S000007282"
                     /Name="NP_009327.1"
                     /Note="Maturase-like protein"
                     /gbkey="CDS"
                     /locus_tag="Q0255"
                     /product="maturase-like protein"
                     /protein_id="NP_009327.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     gene            77431..77505
                     /label="tF(GAA)Q"
                     /ID="gene-tF(GAA)Q"
                     /Dbxref="GeneID:854624"
                     /Dbxref="SGD:S000007319"
                     /Name="tF(GAA)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tF(GAA)Q"
                     /source="RefSeq"
     tRNA            77431..77505
                     /label="tF(GAA)Q"
                     /ID="rna-tF(GAA)Q"
                     /Parent="gene-tF(GAA)Q"
                     /Dbxref="GeneID:854624"
                     /Dbxref="SGD:S000007319"
                     /Note="Mitochondrial phenylalanine tRNA (tRNA-Phe);predicted by tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tF(GAA)Q"
                     /product="tRNA-Phe"
                     /source="RefSeq"
     exon            77431..77505
                     /label="tF(GAA)Q"
                     /ID="exon-tF(GAA)Q-1"
                     /Parent="rna-tF(GAA)Q"
                     /Dbxref="GeneID:854624"
                     /Dbxref="SGD:S000007319"
                     /Note="Mitochondrial phenylalanine tRNA (tRNA-Phe);predicted by tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tF(GAA)Q"
                     /product="tRNA-Phe"
                     /source="RefSeq"
     gene            complement(78089..78162)
                     /label="tT(UAG)Q2"
                     /ID="gene-tT(UAG)Q2"
                     /Dbxref="GeneID:854625"
                     /Dbxref="SGD:S000007335"
                     /Name="tT(UAG)Q2"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tT(UAG)Q2"
                     /source="RefSeq"
     tRNA            complement(78089..78162)
                     /label="tT(UAG)Q2"
                     /ID="rna-tT(UAG)Q2"
                     /Parent="gene-tT(UAG)Q2"
                     /Dbxref="GeneID:854625"
                     /Dbxref="SGD:S000007335"
                     /Note="Mitochondrial threonine tRNA (tRNA-Thr); decodes CUAcodons as threonine rather than leucine; has an unusualenlarged 8-nt anticodon loop; may be evolutionarily derivedfrom mitochondrial tRNA-His; conserved in some genera ofbudding yeasts"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:375006]"
                     /experiment="EXISTENCE:direct assay:GO:0030533 tripletcodon-amino acid adaptor activity [PMID:375006]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0070125mitochondrial translational elongation [PMID:375006]"
                     /gbkey="tRNA"
                     /locus_tag="tT(UAG)Q2"
                     /product="tRNA-Thr"
                     /source="RefSeq"
     exon            complement(78089..78162)
                     /label="tT(UAG)Q2"
                     /ID="exon-tT(UAG)Q2-1"
                     /Parent="rna-tT(UAG)Q2"
                     /Dbxref="GeneID:854625"
                     /Dbxref="SGD:S000007335"
                     /Note="Mitochondrial threonine tRNA (tRNA-Thr); decodes CUAcodons as threonine rather than leucine; has an unusualenlarged 8-nt anticodon loop; may be evolutionarily derivedfrom mitochondrial tRNA-His; conserved in some genera ofbudding yeasts"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:375006]"
                     /experiment="EXISTENCE:direct assay:GO:0030533 tripletcodon-amino acid adaptor activity [PMID:375006]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0070125mitochondrial translational elongation [PMID:375006]"
                     /gbkey="tRNA"
                     /locus_tag="tT(UAG)Q2"
                     /product="tRNA-Thr"
                     /source="RefSeq"
     gene            78533..78608
                     /label="tV(UAC)Q"
                     /ID="gene-tV(UAC)Q"
                     /Dbxref="GeneID:854626"
                     /Dbxref="SGD:S000007336"
                     /Name="tV(UAC)Q"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tV(UAC)Q"
                     /source="RefSeq"
     tRNA            78533..78608
                     /label="tV(UAC)Q"
                     /ID="rna-tV(UAC)Q"
                     /Parent="gene-tV(UAC)Q"
                     /Dbxref="GeneID:854626"
                     /Dbxref="SGD:S000007336"
                     /Note="Mitochondrial valine tRNA (tRNA-Val); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tV(UAC)Q"
                     /product="tRNA-Val"
                     /source="RefSeq"
     exon            78533..78608
                     /label="tV(UAC)Q"
                     /ID="exon-tV(UAC)Q-1"
                     /Parent="rna-tV(UAC)Q"
                     /Dbxref="GeneID:854626"
                     /Dbxref="SGD:S000007336"
                     /Note="Mitochondrial valine tRNA (tRNA-Val); predicted bytRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tV(UAC)Q"
                     /product="tRNA-Val"
                     /source="RefSeq"
     gene            79213..80022
                     /label="COX3"
                     /ID="gene-Q0275"
                     /Dbxref="GeneID:854627"
                     /Dbxref="SGD:S000007283"
                     /Name="COX3"
                     /gbkey="Gene"
                     /gene_biotype="protein_coding"
                     /gene_synonym="OXI2"
                     /locus_tag="Q0275"
                     /source="RefSeq"
     mRNA            79213..80022
                     /label="COX3"
                     /ID="rna-Q0275"
                     /Parent="gene-Q0275"
                     /Dbxref="GeneID:854627"
                     /Dbxref="SGD:S000007283"
                     /gbkey="mRNA"
                     /locus_tag="Q0275"
                     /source="RefSeq"
     exon            79213..80022
                     /label="COX3"
                     /ID="exon-Q0275-1"
                     /Parent="rna-Q0275"
                     /Dbxref="GeneID:854627"
                     /Dbxref="SGD:S000007283"
                     /gbkey="mRNA"
                     /locus_tag="Q0275"
                     /source="RefSeq"
     CDS             79213..80022
                     /label="COX3"
                     /ID="cds-NP_009328.1"
                     /Parent="rna-Q0275"
                     /Dbxref="Genbank:NP_009328.1"
                     /Dbxref="GeneID:854627"
                     /Dbxref="SGD:S000007283"
                     /Name="NP_009328.1"
                     /Note="Subunit III of cytochrome c oxidase (Complex IV);Complex IV is the terminal member of the mitochondrialinner membrane electron transport chain; one of threemitochondrially-encoded subunits"
                     /experiment="EXISTENCE:direct assay:GO:0004129 cytochrome-coxidase activity [PMID:7851399|PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:24478450|PMID:16823961]"
                     /experiment="EXISTENCE:direct assay:GO:0005751mitochondrial respiratory chain complex IV [PMID:1331058]"
                     /experiment="EXISTENCE:direct assay:GO:0006123mitochondrial electron transport, cytochrome c to oxygen[PMID:1331058]"
                     /experiment="EXISTENCE:mutant phenotype:GO:0009060 aerobicrespiration [PMID:11171120]"
                     /gbkey="CDS"
                     /locus_tag="Q0275"
                     /product="cytochrome c oxidase subunit 3"
                     /protein_id="NP_009328.1"
                     /transl_table="3"
                     /source="RefSeq"
                     /phase="0"
     rep_origin      82329..82600
                     /label="Untitled Feature"
                     /ID="id-NC_001224.1:82329..82600"
                     /Dbxref="SGD:S000029671"
                     /Note="ORI5; Mitochondrial origin of replication"
                     /gbkey="rep_origin"
                     /source="RefSeq"
     gene            85035..85112
                     /label="tM(CAU)Q2"
                     /ID="gene-tM(CAU)Q2"
                     /Dbxref="GeneID:854628"
                     /Dbxref="SGD:S000007326"
                     /Name="tM(CAU)Q2"
                     /gbkey="Gene"
                     /gene_biotype="tRNA"
                     /locus_tag="tM(CAU)Q2"
                     /source="RefSeq"
     tRNA            85035..85112
                     /label="tM(CAU)Q2"
                     /ID="rna-tM(CAU)Q2"
                     /Parent="gene-tM(CAU)Q2"
                     /Dbxref="GeneID:854628"
                     /Dbxref="SGD:S000007326"
                     /Note="Mitochondrial formylated methionine tRNA(tRNA-fMet); predicted by tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tM(CAU)Q2"
                     /product="tRNA-Met"
                     /source="RefSeq"
     exon            85035..85112
                     /label="tM(CAU)Q2"
                     /ID="exon-tM(CAU)Q2-1"
                     /Parent="rna-tM(CAU)Q2"
                     /Dbxref="GeneID:854628"
                     /Dbxref="SGD:S000007326"
                     /Note="Mitochondrial formylated methionine tRNA(tRNA-fMet); predicted by tRNAscan-SE analysis"
                     /experiment="EXISTENCE:curator inference:GO:0005739mitochondrion [PMID:9023104]"
                     /experiment="EXISTENCE:curator inference:GO:0070125mitochondrial translational elongation [PMID:9023104]"
                     /gbkey="tRNA"
                     /locus_tag="tM(CAU)Q2"
                     /product="tRNA-Met"
                     /source="RefSeq"
     gene            85295..85777
                     /label="RPM1"
                     /ID="gene-Q0285"
                     /Dbxref="GeneID:9164989"
                     /Dbxref="SGD:S000029023"
                     /Name="RPM1"
                     /gbkey="Gene"
                     /gene_biotype="ncRNA"
                     /locus_tag="Q0285"
                     /source="RefSeq"
     ncRNA           85295..85777
                     /label="RPM1"
                     /ID="rna-Q0285"
                     /Parent="gene-Q0285"
                     /Dbxref="GeneID:9164989"
                     /Dbxref="SGD:S000029023"
                     /Note="RNA component of mitochondrial RNase P;mitochondrial RNase P also contains the protein subunitRpm2p; RNase P removes 5' extensions from mitochondrialtRNA precursors; RPM1 is conserved in bacteria, fungi, andprotozoa"
                     /experiment="EXISTENCE:direct assay:GO:0001682 tRNA5'-leader removal [PMID:3537697]"
                     /experiment="EXISTENCE:direct assay:GO:0004526 ribonucleaseP activity [PMID:8366116]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:11522833]"
                     /experiment="EXISTENCE:direct assay:GO:0008033 tRNAprocessing [PMID:11522833]"
                     /experiment="EXISTENCE:direct assay:GO:0030678mitochondrial ribonuclease P complex [PMID:3537697]"
                     /gbkey="ncRNA"
                     /locus_tag="Q0285"
                     /product="RPM1"
                     /source="RefSeq"
     exon            85295..85777
                     /label="RPM1"
                     /ID="exon-Q0285-1"
                     /Parent="rna-Q0285"
                     /Dbxref="GeneID:9164989"
                     /Dbxref="SGD:S000029023"
                     /Note="RNA component of mitochondrial RNase P;mitochondrial RNase P also contains the protein subunitRpm2p; RNase P removes 5' extensions from mitochondrialtRNA precursors; RPM1 is conserved in bacteria, fungi, andprotozoa"
                     /experiment="EXISTENCE:direct assay:GO:0001682 tRNA5'-leader removal [PMID:3537697]"
                     /experiment="EXISTENCE:direct assay:GO:0004526 ribonucleaseP activity [PMID:8366116]"
                     /experiment="EXISTENCE:direct assay:GO:0005739mitochondrion [PMID:11522833]"
                     /experiment="EXISTENCE:direct assay:GO:0008033 tRNAprocessing [PMID:11522833]"
                     /experiment="EXISTENCE:direct assay:GO:0030678mitochondrial ribonuclease P complex [PMID:3537697]"
                     /gbkey="ncRNA"
                     /locus_tag="Q0285"
                     /product="RPM1"
                     /source="RefSeq"
ORIGIN      
        1 ttcataatta attttttata tatatattat attataatat taatttatat tataaaaata
       61 atatttatta ttaaaatatt tattctcctt tcggggttcc ggctcccgtg gccgggcccc
      121 ggaattatta attaataata aattattatt aataattatt tattatttta tcattaaaat
      181 atataaataa aaaatattaa aaagataaaa aaaataatgt ttattcttta tataaattat
      241 atatatatat ataattaatt aattaattaa ttaattaata ataaaaatat aattataaat
      301 aatataaata ttattcttta ttaataaata tatatttata tattataaaa gtatcttaat
      361 taataaaaat aaacatttaa taatatgaat tatatattat tattattatt aataaaatta
      421 ttaataataa tcaatatgaa attaataaaa atcttataaa aaagtaatga atactccttt
      481 ttaaaaataa aaaggggttc ggtccccccc cttccgtata cttacgggag gggggtccct
      541 cactccttct taattaaatt atcttaatta aattatctta attaaattat cttaattaaa
      601 ttatcttaat taaattatct taattaaatt aaaaggggac tttatattta taaagtaatt
      661 atattattat tattattatt atttatttat tttattttta ttattttatt atatatatta
      721 tatattaata cagatagaag ccaaaaggtc aggcgctttc tttgggagaa agacctagtt
      781 agttcgagtc tatcctatct gataataatt taattaacca ttaaaaaaaa gtatatatat
      841 ttatcataat atattaaatt ttattacatt acaaatgaac acttttattt atatttataa
      901 aaatatgaac tccttcgggg tccgccccgc gggggcgggc cggactccat attattatta
      961 ttataattat tattataatt attattataa ttattattat aattattatt ataattaaag
     1021 agttttggat accaatatga tataatatga tataggaccg aaacccctca ttttatcatt
     1081 tatttataat attataaata aaaaaaaata ttatatatta taataaaatt aatatcataa
     1141 tatattatat tatatattat attatatata tatatatata tattctttta taaaatttat
     1201 attcttctta ttaaaattaa aaagggagcg gacttttaat tatatttaat tatagttttt
     1261 aatcattggt tgagatttca aaataaggta taatatttat attattcttt aacaaatatt
     1321 atattataaa aaaagatata atatttatat tattctttaa caaatattat attataaaaa
     1381 agatataata tttatatatt attattaata ttatttttaa gttccgaaag gagaaactta
     1441 taatttttat atcattattt attattattt ttaatttcaa ctccttttag gtatttccat
     1501 ttaactttca gcagagactt tctaattata attatatata tataaattta aatacattta
     1561 taaaaaagta tataatataa ttatattata tataataata ttattaaatg aagtattctt
     1621 tattattaat tataggatat ctggggtcca ttaataatta ttattgtaaa taataataag
     1681 gacccccccc attatctaat taataaatat ataaataatc attaataaat atattaataa
     1741 ttattaataa atatataaat aatcattaat aaatatataa ataatataat atattataaa
     1801 aatataataa taataattta ttattaaaat ataataattt attataaaaa tataataatt
     1861 tattataaaa atataataat aactcctttc ggggttcaca cctttataaa taataaataa
     1921 taaataataa ataataaata ataaatatta gtattcacta atataaaata ataattataa
     1981 aaataatcat tattaaaaat attattaatt attaaattaa atacaattaa tataatttag
     2041 ttgtttatat aattttaaat aatgtttata tcaatttaat aaaattaaat ttatagttcc
     2101 ggggcccggc cacgggagcc ggaaccccga aaggagttta tctatatatt ataataacta
     2161 tatgaattta attattaaaa ataataaaaa taaggaattt taataagaag taatatttat
     2221 tatataatat ataaaaaaaa tatatatata tatataaaaa tatatataat aagttttatt
     2281 ataatatata ttaaattaat tattatgagg ggttcggtcc ctttccgggc cccaattcat
     2341 ctcatctcat tttatttcat ttcaatatca tctaatctca tttctttata gattttacat
     2401 atatataaat ataaatataa gatattcaca tttatatata atataatata atataataga
     2461 tattcattcc tctttgatta aactaataat taataattaa taattaataa ttaataatta
     2521 ataattattc agtagaactc cttcttaaaa aggggttcgg tccccctccc attagtatag
     2581 tatagggagg ggtccctcac tccttcgggg tccgccccgc agggggcggg ccggactatt
     2641 attaaataat ttataattta ttatttatta atatatttat ataatataat ataatataat
     2701 attattcata ctttttatta atataatata atataatatt attaatactt tctcctttcg
     2761 gggttccggc tcccgtggcc gggccccgga actattaata taaagaaaag agtttcaatt
     2821 atttatttat ttatttattt tttataaaaa taagtccccg ccccggcggg gaccccgaag
     2881 gagtattaat ttaaataatt tatttaatga aattattaat tataaataaa aataataatt
     2941 tttaaagatg taatataaaa ataaatataa tataatttag gataattata taaaatattt
     3001 attatatata gtttttataa agagttttaa aagtgataat ataatatata atatttataa
     3061 gttccggggc ccggccacgg gagccggaac cccgaaagga gttatttata tatatataat
     3121 tataatctta ttaattattt atatatatat ttaatattat ttttatataa ttttatatta
     3181 aagtattata attatatatt taatattatt tttatataat tttatattat ttatttattt
     3241 atttatttat ttaaaaatat tataatcata tatttaatat tatttaatat attttatata
     3301 ttatatcttt tattgattta tatatatata gatttaataa atatatatat atatatatat
     3361 ataaatattc attatatatt tattattatt attattattt attactattt tttattatat
     3421 attaataata tatatattat tagttatggg tatcctaata gtatattatt atttttaata
     3481 ataatttatg atttatgtat aataaataag tagggaatcg gtacgaatat cgaaaggagt
     3541 tatatattat taattattta taattatttt atatattatt aattatttat aattatttta
     3601 tatatttata attattttat atagataggt tagataggat agatagtata gataggggtc
     3661 ccatttatta tttacaataa taattattaa tgggacccgg atatcttatt gttattaatt
     3721 tatatattat tcattattat taatatatat ttaatataat taaatattat attatattat
     3781 attatattat ttattaaaaa aaaatctatt acttattttt tttattaata tataaattat
     3841 ttatataatt tatcattttt atttatatat tattattttt tatatataaa ttaatatata
     3901 tatatattat atatactttt ttttttataa tatatctata tatataaata aatatattat
     3961 attatatttt tatataatat attattaatt attattttaa ttttctattc tattgtgggg
     4021 gtcccaatta ttattttcaa taataattat tattgggacc cggatatctt cttgtttatc
     4081 atttattatt ttattaaatt tattattatt tttaatttat atttatatta tataattaat
     4141 tatatcgttt atactccttc ggggtccccg ccggggcggg gactttatat tttattatat
     4201 aatatattat attcttataa tatatttatt gattatgtta taaaatttat tctatgtgtg
     4261 ctctatatat atttaatatt ctggttatta tcacccaccc cctcccccta ttacgtctcc
     4321 gaggtcccgg tttcgtaaga aaccgggact tatatattta taaatataaa tctaacttaa
     4381 ttaataattt aaataatata ctttatattt tataaataaa aataattata acctttttta
     4441 taattatata taataataat atatattatc aaataattat tatttctttt ttttctttaa
     4501 ttaattaatt aattaatatt ttataaaaat atatttctcc ttacggggtt ccggctcccg
     4561 tagccggggc ccgaaactaa ataaaatata ttattaataa tattatataa tataataata
     4621 atataataat tttatataaa tatatattta tatattaaat taaattataa ttttattatg
     4681 aaaattatat ctttttttta tatttttata taataaaaat atgttatata tatattaata
     4741 ataaaaggta gtgaggatta aataaattat ataataatta taactcttaa ttataaaata
     4801 aatatatata tatatataag tatccatttc catataatct tttaataaat attaataaat
     4861 attaaaaaaa aataatatta taatatttta gtatataatt caataaaatt cattggaggg
     4921 gtaaataata ataatttact aatggcaagt tatagtctta aaggttttta ttttttttat
     4981 taaattaata aaataataat accatttata tattccatta tatatatata tttaataaaa
     5041 ataataatat catttatata ttttattata tattatatat attttatata aaataataat
     5101 aataaattta tatttttata tattattatt aaataataat aatataaata actccttcgg
     5161 ggttcggtcc ccacgggtcc ctcactcctt cttaagaata aaaaggggtt cggtccccct
     5221 cccgttagta cacgggaggg ggtctctcac tccttcttaa aaaataaaaa ggtggaagga
     5281 ctaatataat tttaaataat aattaatact ttaataataa tttgtatttc tttattatta
     5341 atatattaaa tataataata attaatataa ttacaatata ttaatattat caaatattaa
     5401 taaatatact tttttatata atttatttat ttatttattt tttttttatt aaactaatta
     5461 taattgtaat ttcgaaaagg gggtgggagt aaacatatat aatttataat ctatatatat
     5521 atatatataa ttttttaata aatattaata aatatttata aaaagaataa tttatattta
     5581 taatatataa tttatatatt ttatttttat tatacaatta atataaaata taaaatatta
     5641 aatattaaat attaaatatt aaatattaaa tattaatttt tataggggtt atataataat
     5701 tatatttata attatataat attaaaaagg gtatttttat aattattaca tttttatttt
     5761 atttataaaa atattaattt taataagtat tgaatacttt atataatata aatattaatt
     5821 acataattaa taattaaata atatttaata atattattta aatttattat ttataattat
     5881 ttatttataa aattctattt ttattattat tatttttatt ttattattaa agattaatat
     5941 aataattatt aatatattaa aaatctttta ttatattaat atttataaaa aagtatttaa
     6001 taaaaaagat gtataaattt ataaattata taatattatt aatttatata ataataatat
     6061 tataactttg tgattgtcaa tttagttaat cattgttatt aataaaggaa agatataaaa
     6121 aatattctcc ttcttaaaaa ggggttcggt tcccccccgt aagggggggg tccctcactc
     6181 ctttggtcgg actccttcgg ggtccgcccc gcgggggcgg gccggactaa tttaactttt
     6241 aatattaata ttaatattat ttatattttt aatatataaa aataaataat tttattttta
     6301 ttaatagtat attatataaa caataaaata gtattaatta tataaaattt atataaaata
     6361 tatataaatt tattatatat atatatatta atattttaat aaagttttta ttataaattt
     6421 atttatttat ttattataat attaataatt tatttattat tatataagta ataaataata
     6481 gttttatata ataataataa tatatatata tatatattat tatattagtt atataataag
     6541 gaaaagtaaa aaatttataa gaatatgatg ttggttcaga ttaagcgcta aataaggaca
     6601 tgacacatgc gaatcatacg tttattattg ataagataat aaatatgtgg tgtaaacgtg
     6661 agtaatttta ttaggaatta atgaactata gaataagcta aatacttaat atattattat
     6721 ataaaaataa tttatataat aaaaaggata tatatataat atatatttat ctatagtcaa
     6781 gccaataatg gtttaggtag taggtttatt aagagttaaa cctagccaac gatccataat
     6841 cgataatgaa agttagaacg atcacgttga ctctgaaata tagtcaatat ctataagata
     6901 cagcagtgag gaatattgga caatgatcga aagattgatc cagttactta ttaggatgat
     6961 atataaaaat attttatttt atttataaat attaaatatt tataataata ataataataa
     7021 tatatatata taaattgatt aaaaataaaa tccataaata attaaaataa tgatattaat
     7081 taccatatat atttttatat ggatatatat attaataata atattaattt tattattatt
     7141 aataatatat tttaatagtc ctgactaata tttgtgccag cagtcgcggt aacacaaaga
     7201 gggcgagcgt taatcataat ggtttaaagg atccgtagaa tgaattatat attataattt
     7261 agagttaata aaatataatt aaagaattat aatagtaaag atgaaataat aataataatt
     7321 ataagactaa tatatgtgaa aatattaatt aaatattaac tgacattgag ggattaaaac
     7381 tagagtagcg aaacggattc gatacccgtg tagttctagt agtaaactat gaatacaatt
     7441 atttataata tatattatat ataaataata aatgaaaatg aaagtattcc acctgaagag
     7501 tacgttagca ataatgaaac tcaaaacaat agacggttac agacttaagc agtggagcat
     7561 gttatttaat tcgataatcc acgactaacc ttaccatatt ttgaatatta taataattat
     7621 tataattatt atattacagg cgttacattg ttgtctttag ttcgtgctgc aaagttttag
     7681 attaagttca taaacgaaca aaactccata tatataattt taattatata taattttata
     7741 ttatttatta atataaagaa aggaattaag acaaatcata atgatcctta taatatgggt
     7801 aatagacgtg ctataataaa atgataataa aattatataa aatatattta attatattta
     7861 attaataata taaaacattt taatttttaa tatatttttt tattatatat taatatgaat
     7921 tataatctga aattcgatta tatgaaaaaa gaattgctag taatacgtaa attagtatgt
     7981 tacggtgaat attctaactg tttcgcacta atcactcatc acgcgttgaa acatattatt
     8041 atcttattat ttatataata ttttttaata aatattaata attattaatt tatatttatt
     8101 tatatcagaa ataatatgaa ttaatgcgaa gttgaaatac agttaccgta ggggaacctg
     8161 cggtgggctt ataaatatct taaatattct tacataaata ttaatctaaa tattaatata
     8221 aatattaata ttaatagttc cggggcccgg ccacgggagc cggaaccccg aaaggagaaa
     8281 tattaatata aatataaata ttaatataaa tataaatata aatataaata tattttaata
     8341 taatataata taatatataa tatattatat aaatataata tataaataat ataataaaat
     8401 attttaatat atatataata taatataatt attattataa tttaatataa attattatta
     8461 taatttaata taataaataa ataaataatt ataattataa ttataattat aatctcaata
     8521 tataaatgat aaattattat aaatacaaag gaaataattg atttttaaaa tatatttaat
     8581 aaaatatata atataaatta tacttttttt gttattatat aataattata ttaatatatt
     8641 taatagaatt aaactccttc ggccggacta ttattcattt tatatattaa tgataaatca
     8701 ttaattatta ttaataaatt tatttataat atttaatttt atatattatt atttataata
     8761 aaaaaaatta tattataaca atttaatttt aatttttatt tttaaattat aaaattaata
     8821 atttatttgt ttaaataaaa tttataactc cttcggggtt cggccggact attaatataa
     8881 ataaataata aatatttata ataaaataat atacatcttc tttaaataaa aaaaggggac
     8941 attataaata gtatataaat atattatatc ttttttatta ttattattaa taaataataa
     9001 taataattta tatatttata atatatttaa tagttccggg gcccggccac gggagccgga
     9061 accccgaaag gagaatgtat tataattatt acatataatt attattattc acttcttatt
     9121 aaaaataata ctctatataa tttatataat ttattttaat atatatatat ttatatataa
     9181 tataatatat atatttattt attataatca ttttttttta acttaaaata aaacttatta
     9241 taatttatat aatttataat ttttatataa aaataattat ataattttta tttatttata
     9301 taataataat attatttgtt atatattata tattatatat ataataaata aataaataat
     9361 aaataataat aataaggata tagtttaatg gtaaaacagt tgatttcaaa tcaatcatta
     9421 ggagttcgaa tctctttatc cttgataata ataataaaaa tatgtattta tttaattatt
     9481 ttaatatttc tcctttcggg gttccggctc ccgtggccgg gccccggaac tattaatata
     9541 atataatata atataaatat tcatttatct tttttttaat attcttaatt aattaattaa
     9601 ttaatatatt aattataaaa aatatattat aattttatta ttaataagta taaatatatt
     9661 attaataata atttattaaa aatatattat tataatatat taatatatca taattataat
     9721 caatattata ttatttaatt ttataatact taattattaa tatattattc atatatatat
     9781 aaattaaatt aaattaatta tattgaatat ataaatatat atatatataa atatataaaa
     9841 aattatataa attattttaa gtaaaaataa tattaataaa aattatacaa taataataat
     9901 aaatattcat tattatttaa ttaatatctc ctttacttct ttttcctccg ttgaggactt
     9961 attattaagt atattattat atactactta agattatata tataatatat atatatatat
    10021 tatatataaa atataaatat ataaataata taaaaattaa taaaataaat aaaataaatt
    10081 agtccgatcg aatcccctat ttaattaaat taaattaaat taagaaagag ataaatttat
    10141 ataaaatatt atttataatt aattataatt aaattataat ataatataat ataaataata
    10201 atataataaa aataaaaata aaataatatt agattatatt atataattta tataattttt
    10261 taataataat aataaataag tttatttata attataaata taaatataaa tataaataaa
    10321 gaaggtatta tattttataa aatataataa taatacaaaa tttatatttt aataaatatt
    10381 aatataagtt taaagttccg gggcccggca cgggagccgg aaccccgaaa ggagaaataa
    10441 ataatatatt tataaaaaat taaataaata aatattatct atttaaaaat aaatataata
    10501 taatataata taataattct aaatataaat aatatttatt ataattatta taataattgt
    10561 attatttatt aataatatat ataattatat taaaactaat attacattat tttgtatatt
    10621 taaacaatta aattgattat tcttatttgt aatctttatt tattttatta tatcttatta
    10681 atgataaatt ataattatta ttaaaataat aatttacttc ttttgatata aaaataaaat
    10741 aatatagttc cggggcccgg ccacgggagc cggaaccccg gaaggagata aatatattat
    10801 atttttattc ctacctatta aaggtaaaga ctcgattctc ataattaaat ttatatcctt
    10861 cggccggatt aatttatttt atttatattt atatttatag tgaatacctt ttttaatatt
    10921 tatttttaat atttattttt aatattttat ttttaataaa atataatctt gtaagtaaga
    10981 aaagaatttc ggtgattgga accttgaaag gataaatttc ttatttatta taatatttat
    11041 attaatagtt ccggggcccg gccacgggag ccggaacccc gaaaggagta ttattaaaca
    11101 tttaatatat tatattaata tttaatttaa atgattaata tattattata ataatattta
    11161 ttttatatta aaatattata attaatatat atatatttat tttaataata ttattattat
    11221 tattattaaa attattattt ttataaatat atatatatat atatatatat tatttttatt
    11281 cttatataaa ttatataaaa aaaatatata taatatataa ttaattaata tatattattt
    11341 aaattatata ttatttaaaa tactttttat attatatctt ctttaaatta aaatataatt
    11401 attatttata ttataattat ttatgaaata ttattattaa aataaaaaag aggtttagac
    11461 tatatattta ttatttataa acttattata ttatttatta ttaatagttc cggggcccgg
    11521 ccacgggagc cggaaccccg aaaggagaaa taaataaaat aaaaaataat aaatattaat
    11581 attattaaat attatttata ataaatatta atattattaa atattattca tattaataaa
    11641 ttttattatt atttgtaata tattaaatat taataatata tatattattt attataatga
    11701 aaacctatcc tatattatcc tatcatataa tatcatatca tattatatta tatcttatta
    11761 tatgatatat aaagtattca ctctatatga ggttatgatt attatataaa tcttatttta
    11821 tttttatttt tatttggact aataataatt ataataataa ttattgatat gttctaatat
    11881 taataaatac atatttatat tataatataa atattcattt cttactaatt aataaaaagt
    11941 ttttatattc attataatat aaatatataa atatatataa atattttaat aattataatt
    12001 atattaagat attataaata tatatttatt tttttttata aaataaataa ataaataaat
    12061 aattaatatt tttatattat aacttatttt tataataata ataagtattt tattttttat
    12121 tatattatta tttatataat tatatatata ttaatttcaa tttaattaat taattaattg
    12181 gtatttggca tataatatca attaattgta attcttataa gaattaatta attaatatgc
    12241 tttttatata atttatactt ttatatttct ccttccgggg ttccggctcc cgtggccggg
    12301 ccccggaact attattatta tttttattta tttattatta aaatataata ataaatagtc
    12361 cggcccgccc cgcggggcgg acgccggagg agaattatat ttttatataa taatttatat
    12421 ttctatatat atatatatat attatatata aatattatta tatatatttt tatatatatt
    12481 ataattatat tcattaatat tttattatag tggtggggtc ccaattatta ttttcaataa
    12541 taatttatca tgggacccgg atatcttctt gtttttattt attattttat taaatttatt
    12601 ttaattattt atttataatt tatattatac aatttattat ttcgttaata cctttattta
    12661 tattatataa tatattatat tattataata tatttattga ttatattaat acatttaact
    12721 aatgtgtgct ctatatttat tgaatagttt ggttcttatc acccaccccc tccccctatt
    12781 acgtctccga ggtcccggtt tcgtaagaaa ccgggactta tatatttaat actaaaaata
    12841 taactacatt acttttttaa tatatataac aatatatata tatatatata ttaattatat
    12901 aaaatataat actctatatt aaatattatt tttatcaata tttatttata tatataataa
    12961 taataataat aatcaatatt aattatttat atatataaga ttaatattat ttaatatatt
    13021 atgaataatt taattaataa atctttaaat attatcataa aaatataaat taaataattt
    13081 cttatttata ataaagaata ataatatata taaatataat aaagaatgta aataatatat
    13141 atataatata atataatata aaaaatatat atatatataa atatatatat aatatataga
    13201 taataatatt tttatataat ttattttatt attaagtaat aaataataaa aaaatcaata
    13261 tattaaataa tatatttata ttagttcggt ttagttggta ttttgtaatg agtaaaaagt
    13321 aatatataat attaaataat aagtattgat ataagtaata gatataataa taatattatt
    13381 aatattttat ataaataata ttaataatat agattatgaa agagagtatt aatatcatta
    13441 aatatatata tatgttatat aatttaaatg attttaatat atatatatat attatattat
    13501 agattatgat acatttatat aaataatata tatataaaaa ttaattatac tattacttta
    13561 taatataata atatttattt ataaagatat aaaagaattg tttaaagtta taactaaaat
    13621 attatatagt attcattaat aattaatatt ataaattcaa ctattgttat atttataaat
    13681 agaataatat attattatcc tttaagatat aacaataatt atttaaatta aattaaatta
    13741 aatttaatta attttttttt ttaatgaata taataataat aatattatta aaattaatat
    13801 ataaaaaaaa agtaaaaatg gtacaaagat gattatattc aacaaatgca aaagatattg
    13861 cagtattata ttttatgtta gctattttta gtggtatggc aggaacagca atgtctttaa
    13921 tcattagatt agaattagct gcacctggtt cacaatattt acatggtaat tcacaattat
    13981 ttaatggtgc gcctctcagt gcgtatattt cgttgatgcg tctagcatta gtattatgaa
    14041 tcatcaatag atacttaaaa catatgacta actcagtagg ggctaacttt acggggacaa
    14101 tagcatgtca taaaacacct atgattagtg taggtggagt taagtgttac atggttaggt
    14161 taacgaactt cttacaagtc tttatcagga ttacaatttc ctcttatcat ttggatatag
    14221 taaaacaagt ttgattattt tacgttgagg taatcagatt atgattcatt gttttagata
    14281 gcacaggcag tgtgaaaaag atgaaggacc taaataacac aaaaggaaat acgaaaagtg
    14341 agggatcaac tgaaagagga aactctggag ttgacagagg tatagtagta ccgaatactc
    14401 aaataaaaat gagattttta aatcaagtta gatactattc agtaaataat aatttaaaaa
    14461 tagggaagga taccaatatt gagttatcaa aagatacaag tacttcggac ttgttagaat
    14521 ttgagaaatt agtaatagat aatataaatg aggaaaatat aaataataat ttattaagta
    14581 ttataaaaaa cgtagatata ttaatattag catataatag aattaagagt aaacctggta
    14641 atataactcc aggtacaaca ttagaaacat tagatggtat aaatataata tatttaaata
    14701 aattatcaaa tgaattagga acaggtaaat tcaaatttaa acccatgaga atagttaata
    14761 ttcctaaacc taaaggtggt ataagacctt taagtgtagg taatccaaga gataaaattg
    14821 tacaagaagt tataagaata attttagata caatttttga taaaaagata tcaacacatt
    14881 cacatggttt tagaaagaat ataagttgtc aaacagcaat ttgagaagtt agaaatatat
    14941 ttggtggaag taattgattt attgaagtag acttaaaaaa atgttttgat acaatttctc
    15001 atgatttaat tattaaagaa ttaaaaagat atatttcaga taaaggtttt attgatttag
    15061 tatataaatt attaagagct ggttatattg atgagaaagg aacttatcat aaacctatat
    15121 taggtttacc tcaaggatca ttaattagtc ctatcttatg taatattgta ataacattgg
    15181 tagataattg attagaagat tatattaatt tatataataa aggtaaagtt aaaaaacaac
    15241 atcctacata taaaaaatta tcaagaataa ttgcaaaagc taaaatattt tcgacaagat
    15301 taaaattaca taaagaaaga gctaaaggcc cactatttat ttataatgat cctaatttca
    15361 agagaataaa atacgttaga tatgcagatg atattttaat tggggtatta ggttcaaaaa
    15421 atgattgtaa aataatcaaa agagatttaa acaatttttt aaattcatta ggtttaacta
    15481 taaatgaaga aaaaacttta attacttgtg caactgaact accagcaaga tttttaggtt
    15541 ataatatttc aattacacct ttaaaaagaa tacctacagt tactaaacta attagaggta
    15601 aacttattag aagtagaaat acaactagac ctattattaa tgcaccaatt agagatatta
    15661 tcaataaatt agctactaat ggatattgta agcataataa aaatggtaga ataggagtgc
    15721 ctacaagagt aggtagatga ctatatgaag aacctagaac aattattaat aattataaag
    15781 cgttaggtag aggtatctta aattattata aattagctac taattataaa agattaagag
    15841 aaagaatcta ttacgtatta tattattcat gtgtattaac tttagctagt aaatatagat
    15901 taaaaacaat aagtaaaact attaaaaaat ttggttataa tttaaatatt attgaaaatg
    15961 ataaattaat tgccaatttt ccaagaaata cttttgataa tatcaaaaaa attgaaaatc
    16021 atggtatatt tatatatata tcagaagcta aagtaactga tccttttgaa tatatcgatt
    16081 caattaaata tatattacct acagctaaag ctaattttaa taaaccttgt agtatttgta
    16141 attcaactat tgatgtagaa atacatcatg ttaaacaatt acatagaggt atattaaaag
    16201 cacttaaaga ttatattcta ggtagaataa ttaccataaa cagaaaacaa attccattat
    16261 gtaaacaatg tcatattaaa acacataaaa ataaatttaa aaatatagga cctggtatat
    16321 aaaatctatt attaatgata ctcaatatgg aaagccgtat gatgggaaac tatcacgtac
    16381 ggtttgggaa aggctcttta acacgtggca acataggtta atttgctatt tcatttttag
    16441 tagttggtca tgctgtatta atgattttct gtgcgccgtt tcgcttaatt tatcactgta
    16501 ttgaagtgtt aattgataaa catatctctg tttattcaat taatgaaaac tttaccgtat
    16561 cattttggtt ctgattatta gtagtaacat acatagtatt tagatacgta aaccatatgg
    16621 cttacccagt tggggccaac tcaacgggga caatagcatg ccataaaagc gctggagtaa
    16681 aacagccagc gcaaggtaag aactgtccga tggctaggtt aacgaattcc tgtaaagaat
    16741 gtttagggtt ctcattaact ccttcccact tggggattgt gattcatgct tatgtattgg
    16801 aagaagaggt acacgagtta accaaaaatg aatcattagc tttaagtaaa agttgacatt
    16861 tggagggctg tacgagttca aatggaaaat taagaaatac gggattgtcc gaaaggggaa
    16921 accctgggga taacggagtc ttcatagtac ccaaatttaa tttaaataaa gtgagatact
    16981 ttagtacttt atctaaatta aatgcaagga aggaagacag tttagcgtat ttaacaaaga
    17041 ttaatactac ggatttttcc gagttaaata aattaataga aaataatcat aataaacttg
    17101 aaaccattaa tactagaatt ttaaaattaa tgtcagatat tagaatgtta ttaattgctt
    17161 ataataaaat taaaagtaag aaaggtaata tatctaaagg ttctaataat attaccttag
    17221 atgggattaa tatttcatat ttaaataaat tatctaaaga tattaacact aatatgttta
    17281 aattttctcc ggttagaaga gttgaaattc ctaaaacatc tggaggattt agacctttaa
    17341 gtgttggaaa tcctagagaa aaaattgtac aagaaagtat gagaataata ttagaaatta
    17401 tctataataa tagtttctct tattattctc atggatttag acctaactta tcttgtttaa
    17461 cagctattat tcaatgtaaa aattatatgc aatactgtaa ttgatttatt aaagtagatt
    17521 taaataaatg ctttgataca attccacata atatgttaat taatgtatta aatgagagaa
    17581 tcaaagataa aggtttcata gacttattat ataaattatt aagagctgga tatgttgata
    17641 aaaataataa ttatcataat acaactttag gaattcctca aggtagtgtt gtcagtccta
    17701 ttttatgtaa tattttttta gataaattag ataaatattt agaaaataaa tttgagaatg
    17761 aattcaatac tggaaatatg tctaatagag gtagaaatcc aatttataat agtttatcat
    17821 ctaaaattta tagatgtaaa ttattatctg aaaaattaaa attgattaga ttaagagacc
    17881 attaccaaag aaatatggga tctgataaaa gttttaaaag agcttatttt gttagatatg
    17941 ctgatgatat tatcattggt gtaatgggtt ctcataatga ttgtaaaaat attttaaacg
    18001 atattaataa cttcttaaaa gaaaatttag gtatgtcaat taatatagat aaatccgtta
    18061 ttaaacattc taaagaagga gttagttttt tagggtatga tgtaaaagtt acaccttgag
    18121 aaaaaagacc ttatagaatg attaaaaaag gtgataattt tattagggtt agacatcata
    18181 ctagtttagt tgttaatgcc cctattagaa gtattgtaat aaaattaaat aaacatggct
    18241 attgttctca tggtatttta ggaaaaccca gaggggttgg aagattaatt catgaagaaa
    18301 tgaaaaccat tttaatgcat tacttagctg ttggtagagg tattataaac tattatagat
    18361 tagctaccaa ttttaccaca ttaagaggta gaattacata cattttattt tattcatgtt
    18421 gtttaacatt agcaagaaaa tttaaattaa atactgttaa gaaagttatt ttaaaattcg
    18481 gtaaagtatt agttgatcct cattcaaaag ttagttttag tattgatgat tttaaaatta
    18541 gacataaaat aaatataact gattctaatt atacacctga tgaaatttta gatagatata
    18601 aatatatgtt acctagatct ttatcattat ttagtggtat ttgtcaaatt tgtggttcta
    18661 aacatgattt agaagtacat cacgtaagaa cattaaataa tgctgccaat aaaattaaag
    18721 atgattattt attaggtaga atgattaaga taaatagaaa acaaattact atctgtaaaa
    18781 catgtcattt taaagttcat caaggtaaat ataatggtcc aggtttataa taattattat
    18841 actattaaat atgcgttaaa tggagagccg tatgatatga aagtatcacg tacggttcgg
    18901 agagggctct tttatatgaa tgttattaca ttcagatagg tttgctactc tactcttagt
    18961 aatgcctgct ttaattggag gttttggtaa ccaaaaaaga tatgaaagta ataataataa
    19021 taatcaagta atagaaaata aagaatataa tttaaaatta aattatgata agttgggacc
    19081 ttatttagct ggattaattg aaggtgatgg aactattcta gttcaaaatt catcttcaat
    19141 aaaaaaatct aaatatagac cgttaattgt tgtagtattt aaattagaag atttagaatt
    19201 agctaattat ttatgtaatt taactaaatg tggaaaagtg tataaaaaaa ttaatcgtaa
    19261 ttatgtatta tgacttattc atgatttaaa aggtgtatat acattattaa atattattaa
    19321 tggatatatg agaacaccta aatatgaagc atttgttaga ggtgctgaat ttataaataa
    19381 ttatattaat tcaacaacaa ttctacataa taaattaaaa aatatagata atattaaaat
    19441 taaaccatta gatacatcag atattggttc aaacgcttga ttagctggta tgacagatgc
    19501 agatggtaat ttttctatta atttaataaa tggtaaaaat cgttctagta gagcaatgcc
    19561 ttattattgt ttagaattaa gacaaaatta tcaaaaaaat tctaataata ataatattaa
    19621 tttttcttat ttttatatta tgtctgcaat tgcactatat tttaatgtta atttatatag
    19681 tagagaacgt aatttaaatt tattagtatc tcttaataat acgtataaac tatattatag
    19741 ttataaagta atagtggcta atctatataa aaatattaaa gtaatagaat actttaataa
    19801 atattcttta ttatcatcta aacacttaga ttttttagat tgatctaaat tagttatttt
    19861 aattaataat gagggtcaaa gtataaaact taatggtagt tgagaattag gtataaattt
    19921 acgtaaagat tataataaaa ctagaactac gtttacttga tctcatttaa aaaatacata
    19981 tttagaaaat aaataaataa attattatta ctttcttccc ctccgaatcc gtaatatatt
    20041 tacggatata taatctcgta gtgtaaaagg tgtaacgaga ttattaataa gttgccgtaa
    20101 tatattgtaa aatatattat tattacaaca ctatatgcgg gaaaacccta aagtcataat
    20161 ataatattat ccccacgagg gccacacatg tgtggccctc gcggggtatg gtaaatttaa
    20221 ttaagttata aatgtactat agtattaaaa attattatga ataatttccc cacccccatg
    20281 cgaagcatgg gggggggtat aagtatggac aatccgcagg aaaccaaata ataattaata
    20341 tcctgaaaca aagtaagtga aggagatatc ttaaaatata tataatatat attttataaa
    20401 ttattatgta ggatcctcag agactacacg tgttgcaccc attatattat gtataatggg
    20461 ttgaagatat agtccaaata taattgaaag attataataa aatgaactat ttattaccat
    20521 taataattgg agctacagat acagcatttc caagaattaa taacattgct ttttgagtat
    20581 tacctatggg gttagtatgt ttagttacat caactttagt agaatcaggt gctggtacag
    20641 ggtgaactgt ctatccacca ttatcatcta ttcaggcaca ttcaggacct agtgtagatt
    20701 tagcaatttt tgcattacat ttaacatcaa tttcatcatt attaggtgct attaatttca
    20761 ttgtaacaac attaaatatg agaacaaatg gtatgacaat gcataaatta ccattatttg
    20821 tatgatcaat tttcattaca gcgttcttat tattattatc attacctgta ttatctgctg
    20881 gtattacaat gttattatta gatagaaact tcaatacttc attctttgaa gtatcaggag
    20941 gtggtgaccc aatcttatac gagcatttat tttgattctt tggtcaaaca gtggccctta
    21001 ttattatatt aataatatat aatgatatgc atttttctaa atgctggaaa ttattaaaaa
    21061 aatgaattac aaatattata agtctattat ttaaagcctt atttgtaaaa atattcatat
    21121 cttataataa tcagcaggat aagataataa ataatcttat attaaaaaaa gataatatta
    21181 aaagatcctc agagactaca agaaaaatat taaataattc aataaataaa aaatttaatc
    21241 aatgattagc tggattaatt gatggtgatg gatattttgg tattgtaagt aagaaatatg
    21301 tatcattaga aattctagta gcattagaag atgaaatagc tttaaaagaa attcaaaata
    21361 aatttggtgg ttctattaaa ttaagatcag gtgtaaaagc tattagatat agattactta
    21421 ataaaactgg tataattaaa ttaattaatg cagttaatgg taatattaga aatactaaaa
    21481 gattagtaca atttaataaa gtttgtattt tattaggtat tgattttatt tatccaatta
    21541 aattaactaa agataatagt tgatttgttg gattttttga tgctgatggt acaattaatt
    21601 attcatttaa aaataatcat cctcaattaa caatttctgt aactaataaa tatttacaag
    21661 atgtacaaga atataaaaat attttaggtg gtaatattta ttttgataaa tcacaaaatg
    21721 gttattataa atgatccatt caatcaaaag atatagtatt aaattttatt aatgattata
    21781 ttaaaataaa tccatcaaga acactaaaaa taaataaatt atatttaagt aaagaatttt
    21841 ataatttaaa agaattaaaa gcttataata aatcttctga ttcaatacaa tataaagcat
    21901 gattaaattt tgaaaataaa tgaaaaaata aataaattat ttaataaaga tatagtccaa
    21961 attatatata tataatatat atatatataa caagcaccct gaagtatata ttttaattat
    22021 tcctggattt ggtattattt cacatgtagt atcaacatat tctaaaaaac ctgtatttgg
    22081 tgaaatttca atggtatatg ctatggcttc aattggatta ttaggattct tagtatgatc
    22141 acatcatatg tatattgtag gattagatgc agatcttaga gcatatttcc tatctgcact
    22201 aatgattatt gcaattccaa caggaattaa aattttctca tgattaataa atccctttag
    22261 caaggataaa aataaaaata aaaataaaaa gttgatcaga aattatcaaa aaataaataa
    22321 taataatata ataaaaacat atttaaataa taataatata attataataa atatatataa
    22381 aggtaattta tatgatattt atccaagatc aaatagaaat tatattcaac caaataatat
    22441 taataaagaa ttagtagtat atggttataa tttagaatct tgtgttggta tacctctata
    22501 tactaatatt gtaaaacata tagtaggtat tcctaataat attttatata ttataacagg
    22561 tattttatta acagatggtt gaattgatta tctatctaaa aaagatttag ataaaaaaac
    22621 aattatagaa attaattgta gatttagatt aaaacaatca ataattcata gtgaatattt
    22681 aatatatgta tttatattat tatcacatta ttgtataagt tatcctaaaa taaaaattgc
    22741 taaagttaaa ggtaaatcat ataatcaatt agaattttat actagatcat taccatgttt
    22801 tactatttta agatatatat tttataatgg tagagtaaaa attgtaccta ataatttata
    22861 tgatttatta aattatgaat ctttagctca tataattata tgtgatggtt catttgtaaa
    22921 aggtggaggt ttatatttaa atttacaatc ttttctaact aaagaattaa tttttattat
    22981 aaatatttta aaaattaaat ttaatttaaa ttgtctatta cataaatcta gaaataaata
    23041 tcttatttat ataagagtag aatctgttaa aagattattt cctataattt ataaatatat
    23101 tttaccttct ataagatata aatttgatat tatattatga caaaaaaaat ataatatgat
    23161 taattaatta attaattaat taatttattt attatttact tttttgatat atatagaggc
    23221 aaactcgagg aaaaccatat aattagaata agtaataatt atatgacaac cgtcgaacta
    23281 aatcatattc aagaaattaa tatgtaaaag cgtagagatt agacgcctct ggttatctaa
    23341 gtaatatata tatatatatt atatgataac ataaggtata atccaatgag atcagtaatg
    23401 attttaaaac aataattttg ttttaagtat taataataat attaatattc gacctcttaa
    23461 ttgaggatat tataatcata attttttata ttataatata aaatttaact agctagataa
    23521 tattatataa aaaaaaaaaa taatattata taaattaatt aaaataattt ttattaattg
    23581 aaactgaaat gttttaaagt taaataaaag agctctaatc catggtggtt caattagatt
    23641 agcactacct atgttatatg caattgcatt cttattctta ttcacaatgg gtggtttaac
    23701 tggtgttgcc ttagctaacg cctcattaga tgtagcattc cacgatatta atttaataag
    23761 tgtcgtgctt aaaattcact aaaataatat ataataaatt ataataaata tataaaaaaa
    23821 ataaaaaaaa taaaaaaaaa ttaatatctt atgattaatt ttatataaat aaaaatttat
    23881 taaatattat tggttatata tatatatata ttaataataa aaaaatatat atatatatat
    23941 agctaacggg gaaactctta taattattat ttatataata aataagacaa tcccgtgata
    24001 actttaatat atatatatta tatattaaag tattgtagag actaaacgtg aatgatttta
    24061 atattattta aatattaaaa ttaagagata gtccaatctt atatgtaaat ataagttaat
    24121 accaaaaaaa aaataatatt attttgactt attatatatt aatattatta ataataattt
    24181 taactaataa taaagttttt atagaaactt tatattatta tttaatattt aattttcaat
    24241 taatatctcc ttttggggtt ccggtccctg gtccggcccc cgaaactaaa gatattaaga
    24301 atttatatga atcaattata aataattata ttaatatttt aaataaatat cttattaata
    24361 ttaataaaga taatattaat aaattaaaat ttttagataa ttatactgaa gaagaaaaag
    24421 gttattattt atctggatta tttgaaggag atggtaatat ttatactaga tgtttttcaa
    24481 ttactttttc tttagaagat gttttattag ctaattattt atgtctttat tttaaaattg
    24541 gtcatattac agctaaatat aattttaata aagaattaac agctgttaaa tgaaatatta
    24601 taaaaaaaaa agaacaagaa gtatttataa attatattaa tggtaaatta ttaacatata
    24661 aaagatatga tcaatatttt aaatataatt ttaataatcg tttaaatatt aaattattaa
    24721 aacctaaaga atttgattta ctattaaatc cttgattaac aggttttaat gatgctgatg
    24781 gttattttta tctaggtttt caaaaacata aaaatagtca atgattaaaa tttcatttag
    24841 aattatcaca aaaagatagt tatattttag tccggcccgc ccccgcgggg cggaccccaa
    24901 aggagatatt attaaaaaat attttaaact tggtggtatt ttaaaaagag attataaatc
    24961 tggtgctaca gcttatattt ataaagctca atcatcaaaa gctataaaac cttttattga
    25021 atattttaat aattatcaac cattaagtct tagaagatat aaacaatatt tattattaaa
    25081 tattgcttac ttattaaaat taaataaatt acatatatta cttaattctt tattaatatt
    25141 aaaagaatta atattattac aaagtgttaa aaatatatct ttagaaataa aaaatgaatt
    25201 aaataataga gttaaaatta ttattaataa acttcattat aacaatatcg aataatgata
    25261 atattaaaga gtaaaattct taaagtgtta attaaataat attctttttt ttttatgact
    25321 tactacgtgg tgggacattt tcgtgcggtc tgaaagttat cataaataat atttaccata
    25381 taataatgga taaattatat ttttatcaat ataagtctaa ttacaagtgt attaaaatgg
    25441 taacataaat atgctaagct gtaatgacaa aagtatccat attcttgaca gttatattat
    25501 aaaaaaagat gaaggaactt tgactgatct aatatgctca acgaaagtga atcaaatgtt
    25561 ataaaattac ttacaccact aattgaaaac ctgtctgata ttcaattatt atttattatt
    25621 atataattat ataataataa ataaaatggt tgatgttatg tattggaaat gagcatacga
    25681 taaatcatat aaccattagt aatataattt gagagctaag ttagatattt acgtatttat
    25741 gataaaacag aataaaccct ataaattatt attattaata ataaaaaata ataataatac
    25801 caatatatat attatttaat ttattattat tatattaata aaatttaata tatattataa
    25861 ataattattg gattaagaaa tataatattt tatagaaatt ttctttatat ttagagggta
    25921 aaagattgta taaaaagcta atgccatatt gtaatgatat ggataagaat tattattcta
    25981 aagatgaaaa tctgctaact tatactatag gtgatatgcc tatctttatt tatatatata
    26041 ttattattat taataataaa aaaaaaaatt aaaaaaaaga taggaggttt atatataact
    26101 gataaatatt tattatatta ttttttttta taataaatat taaaagatat tgcgtgagcc
    26161 gtatgcgatg aaagtcgcac gtacggttct taccggggga aaacttgtaa aggtctacct
    26221 atcgggatac tatgtattat caatgggtgc tattttctct ttatttgcag gatactatta
    26281 ttgaagtcct caaattttag gtttaaacta taatgaaaaa ttagctcaaa ttcaattctg
    26341 attaattttc attggggcta atgttatttt cttcccaatg cattttttag gtattaatgg
    26401 tatgcctaga agaattcctg attatcctga tgctttcgca ggatgaaatt atgtcgcttc
    26461 tattggttca ttcattgcac tattatcatt attcttattt atctatattt tatatgatca
    26521 attagttaat ggattaaaca ataaagttaa taataaatca gttatttata ataaagcacc
    26581 tgattttgta gaatctaatc ttatctttaa tttaaataca gttaaatctt catctatcga
    26641 attcttatta acttctccac cagctgtaca ctcatttaat acaccagctg tacaatctta
    26701 agttataaaa tttaattatt tacttaataa ttaaaaagta aatattatat ctaaacttaa
    26761 taatataata ataatattct tataaaaata tataaaaaaa aatatataaa atttattaaa
    26821 atatctcctt tcgggaacta taatatattt atataaataa atactaatat aatcctatta
    26881 tatatatata tatataaaat aatatatata tataattaat ataaataata tttataataa
    26941 ttttttaata atatatataa tttaatatat taatgaatat tatataatta ttaaatatat
    27001 tataatatta ttattatttt ataataaaaa tatttttaat actaattatt atttattatt
    27061 tataaatata taaatagtat gtttaatatt attaatacta aaaaaaatat aattataatt
    27121 aggatctaac aatacattta tctgattaat attaatatta atattaatat ttatattaat
    27181 aaacggatta aattaattgt atccaattta attaaattat agatatatta tttataatat
    27241 taatatattg ttttattaaa aaggtaaaaa tagtttttat tttatatata aatataggat
    27301 ataaataaat atattatagt gaaccccgaa aggagaatat attaagaata tatttatatt
    27361 ttacatataa ttatttataa tataaatatc tccgcaaagc cggattaatg taattattta
    27421 ataattttat ttaataattt attaaaataa atatttacat ttgataatat ttatattatg
    27481 tcagttattt tatattaatg tttaatctat tataatattt ttttttataa atatattatt
    27541 tatttatatt aattatatat atatattatt tttataatat atatatattt ttattaaata
    27601 tttattaaat atttattaaa ttattataat gttgttatta atcttattaa aaaatatata
    27661 taaaaatgcc acaattagtt ccattttatt ttatgaatca attaacatat ggtttcttat
    27721 taatgattct attattaatt ttattctcac aattcttttt acctatgatc ttaagattat
    27781 atgtatctag attatttatt tctaaattat aatatatatt attaatttat ttattcatat
    27841 aaatattatt attatatata aatattaata atatttatac ttatttaata ataataaaat
    27901 aaaaaataat tataatttaa tatatttaat atatttcctt acggactata tatttatata
    27961 tatatattaa atacaattta atttaattta attatgttat ttattaaata aagttatatt
    28021 atgatataat aacaatatta tatattatta tataattata atatatttta atataattat
    28081 caaaagaaat aataaaaaaa tattaataag aatataattt aataattatt aaaaaaaaat
    28141 tcttatagtc cggcccgccc cccccgcggg gcggacccca aaggaggagt aataaaaatt
    28201 attaaataca aatattatat atatataatt cattatatat atatatatat aataattaat
    28261 cttatttttt tatatattta tttatatatc tatttatatt ttatatatat ttatttatat
    28321 atctaagggg ttcggtccct ccccccgtaa gtataatata cgggggtggg tccctcacta
    28381 tttatatttt tattttatat attttatata tttataaata aagtataata agatataatt
    28441 atgattaatt atttataagt tatagtttta taaatttata attattatgt ttaatttatt
    28501 aaatacatat attacatcac cattagatca atttgagatt agactattat ttggtttaca
    28561 atcatcattt attgatttaa gttgtttaaa tttaacaaca ttttcattat atactattat
    28621 tgtattatta gttattacaa gtttatatct attaactaat aataataata aaattattgg
    28681 ttcaagatga ttaatttcac aagaagctat ttatgatact attataaata tgcttaaagg
    28741 acaaattgga ggtaaaaatt gaggtttata tttccctatg atctttacat tatttatgtt
    28801 tatttttatt gctaatttaa ttagtatgat tccatactca tttgcattat cagctcattt
    28861 agtatttatt atctctttaa gtattgttat ttgattaggt aatactattt taggtttata
    28921 taaacatggt tgagtattct tctcattatt cgtacctgct ggtacaccat taccattagt
    28981 acctttatta gttattattg aaactttatc ttatttcgct agagctattt cattaggttt
    29041 aagattaggt tctaatatct tagctggtca tttattaatg gttattttag ctggtttact
    29101 atttaatttt atgttaatta atttatttac tttagtattc ggttttgtac ctttagctat
    29161 gatcttagcc attatgatgt tagaattcgc tattggtatc attcagggat atgtctgggc
    29221 tattttaaca gcatcatatt taaaagatgc agtatactta cattaaatta taaaataaaa
    29281 ttataaaata aaataattta catatggagt attaaactat aataaataca atatacccca
    29341 tccccccctt ttaataatat tcttttatct aataaaatat ttatttatta atattattat
    29401 tatcttcttc aaggacttat ttaatatatt taataactta ttatacttat ttatatttat
    29461 aattaataca aatatattat taatcttact ccttcggagt tcggcccccc ataagggggg
    29521 gacctcactc cttccccact gcactggatg cggggactta tttttattat tattatttaa
    29581 tctttattta taaaattata tattatatat aaattattat acttaataat taaaaaaaaa
    29641 cctctaatta ttattaatat tatatataat atatatattc tcattaatgt tatatataat
    29701 atatatattc tcattaatat attaatatag tattaaaaaa aataaaatat ttaataaata
    29761 ttattattaa taatatttat taaaaataat ataacataat aaatataaga ttattatata
    29821 atatatttat tatatcatat agttccgggg cccggccacg ggagccggaa ccccggaagg
    29881 agaaattata acatattttt taataatatt catatttatt ttatatacaa ataaatatat
    29941 ttatttagaa taataaaaaa aaataataaa taaatatatt attatcatta ttatacttta
    30001 ttcattattt attataataa ttatatataa caattataat atataattat attttatata
    30061 atattatatt aatatttaat atatttatta ttattattac ttctatggaa actttatatt
    30121 ttagatattt ttattattat tattaattta taatgttata tttttgattt ataaatatat
    30181 aagtcccggt ttcttacgaa accgggacct cggagacgta atagggggag ggggtgggtg
    30241 ataataacca gaatattcaa taaatacaga gcacacatta gataaatttt ataatataac
    30301 caatataaaa taaaattaaa ataattaata tatatatata aatataataa attattatat
    30361 ataaatatat ataattttta taataaatat tataatatta tataaataaa taattataat
    30421 atataataaa tatataataa taataaaaat attaacaata taataaaaat ttataatata
    30481 aatataaatt ataaataagt taaattaata aaataataaa tgattaacaa gaagatatct
    30541 ggggtcccat taataattat tattttcaat aataattggg accccccacc attataatat
    30601 catattaatt aatataataa taatgtatat aaaatagaaa taataattaa tataataata
    30661 ataatatata taaaatagaa ataataatta aatatatata taaataatta tttatataat
    30721 atattataaa taataataat aataaatatt tattaattaa taatgattat aaatatttta
    30781 tttaatataa atttataact attttattat atatatattt tttattcata aaaattcctt
    30841 ttgaggattt ttattttata taaatatctt ctaatattta taataaataa taatatattc
    30901 attatattta taattatata taatgtaata cgggtaaaca ttacccgttg ttcacgggta
    30961 atgtttaccc tattttatat aattcttaat aaatatattt atatttttat ataaaaaaaa
    31021 ttataataat ttattaattc tcctttcggg gttccggctc ccgtggccgg aactccggaa
    31081 ctataaaaat aattttaata taatttatat attttatgat taatataata tattattaat
    31141 gtaactcctt cgggatttgg tccccctcgt aagtatatag tatatagtat atagtatacg
    31201 gggggtccct cactccttcg gggttcggtc ctcccttacg ggtacggata cggatacgaa
    31261 tatggggagt ccctcactcc ttatcactac gctgaaggtg gaatttattt tatattatta
    31321 ttaaatcttt atttatttaa ttatatattt aatatatata ttattataat aaaacaccta
    31381 attattatta atgttatatt taatataata tatatattct taaaaattta tataatataa
    31441 ataaataaaa aaaaaagaaa gtacataatt aatattatta taaataatat tattaaaaag
    31501 aatataatat aattaataga aagacgtttt aaaaataaaa ataaaaataa aaataaaaat
    31561 aaaaataaaa ataaaaataa aaataaaaga gttttggttt acatatcaag acccaattca
    31621 attgaaacta tttatttatt aatctcctcc cctccccctc actattatta taagtacaat
    31681 tagggcgcca accccgcagt gttatttact gggaaatgtt tatcccaatt aatataataa
    31741 cgagagttat taattattat ttataaattc atataatgta atataatgta atgtaattaa
    31801 tagaacatta ttgtgttatt caccagtgtt aagatatatt aatcccaatt ttatttaata
    31861 gtgaagatta tattttatta attatgaatc catattatta ttatttaata tatttataat
    31921 attatatata attataatta taaataattt atataaaaaa agttttatta aaaaatatta
    31981 ttaaaaatat aatattaata ataaataaaa ataatattat actcttaata gaatttataa
    32041 tgataaaaat taagatgaag actttttttt ataattatta taaatttata taaaaataat
    32101 atatatatat ttatatttat tttattaata tatataatat atttatgtat attaaaaaga
    32161 tatatttaaa tatttttatt ttttttttat aagataattt ttgtaaatat ataagtaata
    32221 aattaagttt tataggggga gggggtgggt gattagaaac ttaactgaat aatatatata
    32281 aagcatacat tagttaatat ttaataatat aatcaatata taataattat aaaataatta
    32341 attatataat aataataatg tataaacaat ataataaatt gtataaaata aaatataaat
    32401 cataaataaa gctaaattaa taaaataata aatgataaac aagaagatat ccgggtccca
    32461 ataataatta ttattgaaaa taataattgg gaccccatat agaatataaa taattaaata
    32521 tatatatata aataataatt tatataatat attataaata aataataata aatattatta
    32581 atctataata attataaata ttttattaat ataaatttaa taattatata tatttttata
    32641 ataactccga aagagtaagg agatattaat ttcttataaa aatttattaa taataataat
    32701 atataaaata tataaataat atattatata taaaataaaa taaaataaat aatatattaa
    32761 aaatattgaa agtattttaa taaataataa atttaaaatt catatttata ataataaata
    32821 aataaataaa taaataagta aatatttaga ttctcattaa tattaatatt tatatttctt
    32881 tttttttata ataataaaaa tatcatatat aaatataata taatataata taataaatta
    32941 ttatatataa ataataaata ttaaatataa tatataataa tatataatct tacaatttat
    33001 aatttaataa agaaggaaat aaataataat aactcctttt ggggttccgg tggggttcac
    33061 acctttataa ataataaata aagatgttta ctcctcttcg gggttccggt cccctttttg
    33121 ggttccggaa ctaattaata ttttatataa taataataat atattaatat aatttcatta
    33181 ttaataaata tctcctgcgg ggttcggttc ccccccgtaa ggggggggtc cctcactcct
    33241 tcggagcgta ctattattat aaataattat atattataat ataattaaaa agtattataa
    33301 ttgaaacgaa aattgtaatt ttaaatggaa taataattat tatatattta atatatttaa
    33361 taaagttata atatctcttt ctaccggact attttatttt attttatttt atttttataa
    33421 agaaaaatag taataatatt atcttctcct cctttcgggg ttccggttcc cgtgccgggc
    33481 cccggaacta ttaattatat aatataatat aatataatat aatataatat gatacggatc
    33541 aaacattacc cgttgttcac tggcaatgtt taatcctatt gtatataaat ataataaaat
    33601 aattatccct ctcgtaatac atatataaaa tataaaatat aaaataaaaa tattatgatt
    33661 attataatat atatatatat atatataaat atatatatat aatttataat ttatatgatt
    33721 aatatattat atatataaaa aatatattaa atttactttt tatagaaagg agtgagggac
    33781 ccccccccct tacggggggg aaccgaaccc cgcaggagat atttatttta atacttatat
    33841 agtatttatt aataatataa taattgttat tataaatatt aataataata taaaaatagg
    33901 gtaaataata taaataatat gaataaatat aaaaacatat taaatataaa atatatcata
    33961 aatttaataa atattataat aatttataaa tgatagatat ctggggtcct ataaataata
    34021 attattttca ataattatag ggacccccac ctattatata aatataaata taaatataaa
    34081 tataaataca aatataaata tataaatata taaatataat ataaatacaa atataatata
    34141 taaatataaa tataaatata taaatataag tccccgcccc ggcggggacc ccgaaggagt
    34201 gagggacccc tccctatact aatgggaggg ggaccgaacc ccgaaggagt ataaataaaa
    34261 attaataata tatatataat tataatagtt ccggggcccg gccacgggag ccggaacccc
    34321 gaaaggagaa ataataatat aatatataat aaaatataac ttattaatat aatattaaaa
    34381 atataattaa caagaataaa tagtccgtgg gatcgaaccc ccttttttat ttaatattta
    34441 atatttaaag aaggaattgt ttatatatat taatatctta tttggggatt aatataatat
    34501 ataagttttg gataccaggc caaagaccgg aatcccaaaa ggagattata taaatattat
    34561 ttatctccct tttttaatat tataataatt ttattaaaaa taaaataata ataataatta
    34621 taatttataa taacaattat aataatttaa ttaattaatt aattaattaa ttaattaatt
    34681 aattaattaa taataaatat aaatataaaa agaatataat ttataataaa taaatttata
    34741 tatatatata tatattaaat aaaatattta cttcattaat ataaaatata aatatattta
    34801 attaataagt atatatatat aataatatat aataacctat ttatatatat aatcttaata
    34861 taattataag aaatattata taagtaatat ataaaaataa tataaaataa ttataattca
    34921 atttatatat taatagttcc ggggcccggc cacgggagcc ggaaccccga aaggaggaat
    34981 aagataaata tataaattat attaataaat ataaatttta aatgaattaa taaaattaat
    35041 atatatatgt atatatatat atatattaaa aatatttaat tatttttagg aaggagtgat
    35101 agatcccttt gggggaccga acccctattt aagaaggagt gcgggacccc gtgggaaccg
    35161 aacccctttt ttatttaaag aagaagtttt attttatttt attttatttt attttatttt
    35221 attttatttt attttatttt attttattta atttaatttt aattaggtta ataaatagta
    35281 ataataaact taataataat aataataatt ttatttttat aatttattaa taataataat
    35341 aattatatat atatatatta ttaataaata tagaccttat cgtctaatgg ttacgacatc
    35401 acctcttcat gttgataata tcggttcgat tccgattaag gttattcata ataataaata
    35461 tttgtaaaaa aagtatatat aattaaacat attctttata ttaattaata attattaata
    35521 atatacattt tatataatac aattatatat atatatatat ttttttttaa tacaaataat
    35581 atattcataa taataaatac cgattgttat tatactataa taaaatatat aatatatttt
    35641 tcattataat atttttaaat aaatattata ataaattata taaataatat ttatgtataa
    35701 taataataat aataattgtt attaattaat tctataatta ttatatattt aatttttttt
    35761 tttaatataa tatataataa tataatttat tttatttttt tttatagttc cggggcccgg
    35821 tcacgggagc cggaaccccg aaaggagaat ataaattaat aataatataa ataacatatt
    35881 aacaataaat tattgttaat ataataataa taataacaat attaataaat aatataaaaa
    35941 ttattaatat tatatttata taatattaat ataaaaatct ttcataatat taattattat
    36001 taaataataa tgatatcatt aatattaata taatcgtcaa tattatttat ttatttattt
    36061 atttatttat ttatttattt atttattatt aaataaatat ttttaaatat tatattatat
    36121 tattaacttt ttattaaaaa aattaataat gatataatat aattaatatt atccacggga
    36181 ccaatgacca acccagtagt tgaccggatt ggcgcccgcg aggtttatat ttaataaata
    36241 ataataataa tattaataaa atctattaac tttttttttt aatggattat attaatgaaa
    36301 aaaaaatgag aaatatcttt tttttttaat aattataatt tatatataat aaaatatgta
    36361 tatataataa aaaaatagtt tttaatatta taatataatt atatatataa ttataaatat
    36421 atatatatat aataagtatt aattaataat atatatttat atatttttta ttaattaata
    36481 tatataaaat attagtaata aataatatta ttaatatttt ataaataaat aataataata
    36541 tggcatttag aaaatcaaat gtgtatttaa gtttagtgaa tagttatatt attgattcac
    36601 cacaaccatc atcaattaat tattgatgaa atatgggttc attattaggt ttatgtttag
    36661 ttattcaaat tgtaacaggt atttttatgg ctatgcatta ttcatctaat attgaattag
    36721 ctttttcatc tgttgaacat attataagag atgtgcataa tggttatatt ttaagatatt
    36781 tacatgcaaa tggtgcatca ttctttttta tggtaatgtt tatgcatatg gctaaaggtt
    36841 tatattatgg ttcatataga tcaccaagag tactattatg aaatgtaggt gttattattt
    36901 tcattttaac tattgctaca gcttttttag gttattgttg tgtttatgga cagagtgaga
    36961 caagtataag tatattatta taatatcata ccattaaata aattatttta atgaaatgat
    37021 tatgtttata tataacatat acctaattag acatgcatta ttagtaataa ttttgtatga
    37081 aactctaata ataataatta ttattaatta ttaaggtaag attcatatgg atagcgtaag
    37141 tcaatctaat attataaaat atcgtaacat aaacaatatt tttttctatt attaattaat
    37201 aaataataat aaataaaaat aattatatga gaagtaagat attcaattct gtctagaata
    37261 catatatata cgttaatact catcggtata aaattagaat cctaagtgaa ttattgaaag
    37321 tataataata taaacttggt aagcccaatt atttccatat aatattaata taaatattat
    37381 atggtagtta tatataatat tattaaataa ataataatag aaattataat atagataagt
    37441 gggtaaaaga ctattgaaaa agctaaagat tatatgtaat gtataatata gatcaaatta
    37501 tttatatatt ttaataaaaa tatattaata atggttaata ttattattaa ttaattaatt
    37561 aattaattaa taataataac gaataaatga ttaatgtgaa agcatgctaa cttcaatata
    37621 ggatgattta tatagtatat aaattgtttg agctgtatac tatgaaagta gtacgtacag
    37681 ttctgagtgg gggaaaattt gtaaagatct acctatcaca attgtcacat tgaggtaata
    37741 taaatatcgc ctcaaatata tttaatataa taaaactaat ttatataata atgttaatat
    37801 tattaattta tattttttat acgattataa taagacaaat aataaaaact aaagaatatc
    37861 ttatattaat taagagtata gattatatta ataaaaataa atatataatt aatttaaata
    37921 taacaaataa gaaagatata aataataata ttggtccatt aaatataaac attttatcaa
    37981 ttatttatgg ttcaatatta ggagatggtc atgctgaaaa aagaaaaggt ggtaaaggaa
    38041 caagaattgt atttcaacaa gaatattgta atattaatta tttatattat ttacatagtt
    38101 tattagctaa tttaggttat tgtaatacta atttaccttt aattaaaact agattaggta
    38161 aaaaaggtaa aattagacaa tatttaaaat ttaatacatg aacttatgat tcatttaata
    38221 tgatttattc agaatggtat attaaaaata tatctggaaa aggtaatatt aaagttattc
    38281 ctaaatcttt agacaattat ttaactcctt tagctttagc tatttgaatt atagatgatg
    38341 gatgtaaatt aggtaaaggt ttaaaattca caactaattg ttttagttat aaagatgttc
    38401 aatatttact ttatttatta cataataaat ataatattaa atctactatt cttaaaggca
    38461 ataaagaaaa tacacaattt gttatttatg tatgaaaaga atctatacct attttaacta
    38521 aaattgtatc tccttatatt attcctagta taaaatataa attaggtaat tatttataat
    38581 aaaatatata gtattatatt aattattata ttattataat gcgatattat tgaaaacatg
    38641 tcaaaattat attattaagt aacaagacag tgggttatat aattatatga tcccaacaga
    38701 atacaccaat aataggtatt attataaaaa aaataataat atttaatgtt tattcgaaga
    38761 aaatttataa tattattatt ataacacaag gtttaataat ctatatatat atattatata
    38821 tataactact gttattattc catttaccta attaatatat aaataatgaa ttataattat
    38881 tatgattaat atttttataa taataacccc atcataacat ttatatataa catttatata
    38941 taacatttat atataatatt tatattatgg tattattagg tataaatatt tattcataag
    39001 agaaaatagt gattaaatgg aattataaaa agggtagata ttattaaata cagggtatta
    39061 tttatattaa taaatcaata aatattgaga ttattattat taaaaaataa taataattta
    39121 taaataatat tattttcttg gcactagtta ttactaattt attctcagca attccatttg
    39181 taggtaacga tattgtatct tgattatgag gtgggtttaa tatagaggat ccatattata
    39241 gtaatataat attaaataaa tctgttttat gctgaaatat cttcatttga ataataaatt
    39301 actatattat tcaattaatt atttataata atataatttg aaataaaaat aatatagtta
    39361 aaatatttat tataagaaga aaattagcag taattaatat atatatatat ataaaattaa
    39421 ttattcagag actttatagt tattatataa ataatactat tatttatgat aaaaatcata
    39481 aattaaacac agataatcct atttatgcat atattggtgg tttatttgaa ggagatggtt
    39541 gaattactat ttcaaaaaaa ggtaaatatt tattatatga attaggtatt gaaatacata
    39601 ttagagatat tcaattatta tataaaatta aaaatatttt aggtattggt aaagtaacaa
    39661 ttaaaaaatt aaaaataaaa gatggtacta ttaaagaaat atgtaaattt aatgtaagaa
    39721 ataaaaatca tttaaagaat attattattc ctatttttga taaatatcct atattaacta
    39781 ataaacatta tgattattta tattttaaag ataatttatt aaaagatatt aaatattata
    39841 atgatttatc ttattattta cgtcctatta aaccatttaa tactcttgaa gatattttaa
    39901 ataaaaatta tttttcttca tgattaattg gtttttttga agctgaaagt tgttttagta
    39961 tttataaacc tataaataaa aaaataaaac ttgctagttt tgaagtatct caaaataata
    40021 gtatagaagt tatattagct attaaatcat atttaaaaat tactcaaaat atttatacag
    40081 ataaatttaa taattcaaga ataacactta aaagtattaa tggtattaaa aatgttgtaa
    40141 tatttattaa taataaccct attaaattat taggttataa aaaattacaa tatttattat
    40201 tcttaaaaga tttacgtctt attcttaaat ataataatta ttttaaaatt cctcctaaat
    40261 attaatctta tataaaaata taataataat atatttatat attatataat tatataaaca
    40321 aaatataatt tatatataat tatttattat aaatatagtc cggcccgccc cgcggggcgg
    40381 accccggagg agtgagggac ccctccctat tctaacggga gggggaccga accccgaagg
    40441 agtttaatta tatattaaat atattattat caataaataa ttcctttgaa ctatttatta
    40501 ttttattata tttattttct ccttcattat taatttttat taataattaa aatcttatca
    40561 ttttatggta tttttatttc tattttagga tatcgaaact ataaattaaa aagtataatt
    40621 ttattaatta taatttatga ttaataaata agaaataaaa actttagaag taatatttat
    40681 cttttttttt tataaataaa tattatgatt aatatataat catttataaa tatttatata
    40741 taattatata tatacataaa taggattaag atatagtccg aacaatatag tgatatattg
    40801 ataatagttt tcaaatatgt aactatttaa acattaaaag ctcagtatct aaccctctaa
    40861 tccagagatt ctttgcgtta cattatttag taccttttat cattgctgca atggttatta
    40921 tgcatttaat ggcattacat attcatggtt catctaatcc attaggtatt acaggtaatt
    40981 tagatagaat tccaatgcat tcatacttta tttttaaaga tttagtaact gttttcttat
    41041 ttatgttaat tttagcatta tttgtattct attcacctaa tactttaggt caaaatatgg
    41101 ccttattatt aattacatat gtaattaata ttttatgtgc tgtatgctgg aaatctttat
    41161 ttattaaata tcaatgaaaa atttataata aaactctata ttattttatt attcaaaata
    41221 ttttaaatac aaaacaatta aataatttcg tattaaaatt taattgaaca aagcaatata
    41281 ataaaataaa tattgtaagt gatttattta atcccaatag agtaaaatat tattataaag
    41341 aagataatca gcaggtaacc aatataaatt cttctaatac tcacttaacg agtaataaaa
    41401 agaatttatt agtagatact tcagagacta cacgcacact aaaaaataaa tttaattatt
    41461 tattaaatat ttttaatata aaaaaaataa atcaaattat tcttaaaaga cattatagta
    41521 tttataaaga tagtaatatt agatttaacc aatgattggc cggtttaatt gacggagatg
    41581 gttatttttg tattactaaa aataaatatg catcttgtga aattcttgta gaattaaaag
    41641 atgaaaaaat gttaagacaa atccaagata aatttggtgg ttctgtaaaa ttaagatcag
    41701 gtgttaaggc tattagatat agattacaaa ataaagaagg tataattaaa ttaattaatg
    41761 ccgttaatgg taatattcgt aatagtaaaa gattagtaca atttaataaa gtatgtattt
    41821 tattaaatat cgattttaaa gaacctatta aattaactaa agataatgct tgatttatag
    41881 ggttctttga tgctgatggt actattaatt attattattc cggtaaatta aaaattagac
    41941 ctcaattaac tattagcgtt acaaataaat atttacatga tgttgaatac tatagagaag
    42001 tatttggtgg taatatttat tttgataaag ctaaaaatgg ttattttaaa tgatctatta
    42061 ataataaaga attacataat attttttatc tttataataa aagttgtcct tctaaatcta
    42121 ataaaggtaa acgtttattt ttaattgata aattttatta tttatatgat ttattagctt
    42181 ttaaagcacc tcataatact gctttatata aagcttgatt aaaatttaat gaaaaatgaa
    42241 ataataatta aattttctcc gtattcatta ttatattatc taatttataa aatatttaaa
    42301 gattccttat aataatataa catctttgta aattattgtt aaagataata taaattatta
    42361 tgaatcggta gattatattt ttacaatctt attaaataaa attctgatca ttaaacatga
    42421 ttgaagaaat aataatagtt tatgaaataa gatagtgtaa tataaatttt tatgaagata
    42481 tagtccattt tatatttatt ataaaagcat cctgataact atattcctgg taatccttta
    42541 gtaacaccag catctattga tattaaaaat attaataaaa ttattattat ttaatcttat
    42601 ttattttata taaaaaaaat aaataataat tattaataaa aatatattat ttatttctcc
    42661 tttcggggtt atttatatat attcctttat aatttatatt taatatatta tattaaatat
    42721 atgaaaaatt ataataaata aattaattaa ttaataataa ataataataa aaagtacagt
    42781 agcattaaat attcttaagt ttccgctttg tgggaactcc cataaggagt ttaatgatta
    42841 aaattggtta attgtcaaga aaatctaagg tattaataaa taaataatac tatgacaact
    42901 tgcagcgaag tttatatcat ctctatatta tatattaata tatatatata ataataataa
    42961 taatattaat ataatataag atataaaaac gttcaacgac tagaaagtga actgagatag
    43021 taataccttt ccacgaaaac caattaattt ataaattatt tttaaataaa gaatagatta
    43081 ttaatttttt ttatatagtt ccgggccccg gccacgggag ccggaacccc ggaaggagta
    43141 atatatatta tatataaaat aaaaaatata tatatatata ttataaaata tcaaaagttt
    43201 taatctttta ttataaatta atgacatagt ctgaacaata atgaaaatta ttgagataag
    43261 atattaaata atcttatgtt aacatatata aattgtgtac ctgaatgata cttattacca
    43321 ttctatgcta ttttaagatc tattcctgat aaattattag gagttattct aatgtttgca
    43381 gctattttag tattattagt tttaccattt actgatagaa gtgtagtaag aggtaatact
    43441 tttaaagtat tatctaaatt cttcttcttt atctttgtat tcaatttcgt attattagga
    43501 caaattggag catgccatgt agaagtacct tatgtcttaa tgggacaaat cgctacattt
    43561 atctacttcg cttatttctt aattattgta cctgttatct ctactattga aaatgtttta
    43621 ttctatatcg gtagagttaa taaataatat ataattaaat taatacatag atataatata
    43681 tatattatta ttattaataa tataataaaa ataaaaataa aattattaat aataataata
    43741 ctttaataat attcttaaaa ataatatatc tctaatttat aaaaattaaa taataataat
    43801 aaaaaaaaaa tattataaaa tataaattaa ttaataatga aaataatata cttattaaat
    43861 taatataaat aaatgaataa tataatataa ctatattgaa ttataatcta tctatctttt
    43921 tttttcatat aattataata tatatattaa tatatataat tattatttta tatattatag
    43981 ttccggggcc cggtcacgga agccggaacc ccgcaaggag atttattaat tattattatc
    44041 attattattt tttatttaat cttatttatt ataaaataat taattatcat aaagcataat
    44101 tattatagaa tcttattatt ttctttattt aaatttataa aaatataaag tccccgcccc
    44161 ctttttattt tatttaatta agaaggtatt ttaaaaaagg agtgagggac cccctcccgt
    44221 tagggagggg gaccgaaccc cgaaggagta ctcatttaat ataaatatta aataaaaatt
    44281 attttatata tattaatgat tattaatatt gataatataa attattttat aattaattat
    44341 tataaatata taactattaa taattaattt ttaatctagg ggtttccccc acttacataa
    44401 acttacgtat acttacatat acttatgtat acttacatat acttacgtat acttatatat
    44461 acttatgtat acttacgtat acttacatat atgggggatc cctcactcct ccggcgtcct
    44521 actcacccta tttattaatc attaataaga aattattatt aaaaaaatta taatttactc
    44581 aaagttaatt ataaatatat ttttaaatat ctattttatt aatcttttat aaaatttaaa
    44641 ttaattgtaa ttaattaata ttataataat tattcttagg aaggatattt atttatttta
    44701 attatgaatt cctgacatag agacaattaa ttagaacttc ttattattat tatagtaata
    44761 ataaaaatat tctaaatata ttatatatat tattattttt tttattatta ataaaatatt
    44821 ataataaatt taaataagtt tataattttt gataagtatt gttatatttt ttatttccaa
    44881 atatataagt cccggtttct tacgaaaccg ggacctcgga gacgtaatag ggggaggggg
    44941 tgggtgataa gaaccaaact attcaataaa tatagagcac acattagtta atatttaata
    45001 atataactaa tatataataa ttataaaata attaattata taatataata taaagtcccc
    45061 gccccggcgg ggaccccaaa ggagtattaa caatataata tattgtataa aataaattat
    45121 aaatattaaa taaaaaccaa ataaataata taataaatga taaacaagaa gatatccggg
    45181 tcccaataat aattattatt gaaaataata attgggaccc ccatctaaaa tatatatata
    45241 actaataata tattatatat attaatatat aataatatta ttaaaatata atattattaa
    45301 aaaaaaagta tatataaaat aagatatata tatataaata tatatattct taataaatat
    45361 tatatataat aataataaat tatttcataa taaattattt ctttttatta ataaaaatta
    45421 cttatctcct tcgaccggac tattaaatat taaatattta atatttaata tttaatattt
    45481 tattctatag atattcatat gaaaaataat aagtatataa ttatgataat gaatatattt
    45541 ttatttataa tttattatta taaaaatatt ttaatttaat aataataata aatcattata
    45601 ttaattcttt taagaattta taattgtcat tatttattat atactcctta ttaaaaggga
    45661 ttcggtttcc ctcatcctca tgggtatccc tcactccttc tgataattaa ttttataata
    45721 ataataaaat aaacttaatt aaatattata tatttattta caattatata tatatattac
    45781 tcataattaa attaaattaa gatgcaattc aatacggttg tattatatta ttcatcaaat
    45841 attgttaata ttgataccta cagagatatt taatattttt attattatta tccattactt
    45901 tttttattat attttaatta tttatttatt tatttattta taataataat atttcatatt
    45961 atcaattatt attttttttt tttataatat ataattaaat tatttatata gttccccgaa
    46021 aggagaataa ataaaatatt atataaatat ttatatcttt attaatatta atataagtaa
    46081 tatatatagt ttatgatatt taattttatc ataatataat aataattata taaatcttat
    46141 acacatttat ataagtatat atatatatta ttaatataat gaacatctat taaataaaat
    46201 aattgtaaat ctcaagtaaa ttattattat tttattttta ataataattt atgatttata
    46261 attaataaat aaaagagtaa ttatatgata aaaaaggtaa taaataaaat ttatagttcc
    46321 ggggcccggc cacgggagcc ggaaccccga aaggagttta tttatatata tatatatatg
    46381 aattaatatt taataataaa taataatata attaataata ttattattat tataattttt
    46441 ttatttataa tattaataaa atattattat atatatatta taataatatt aataagatat
    46501 ataaataagt cccttttttt ttatttaaaa taaagaaaga ataattaaat aatattttaa
    46561 taatttaatt aaatagtgta ttaaaagata ataaaaagta atattaatat gttaattata
    46621 tataatatat ttatatataa ttatatatat atatataaat aataataaat atatatataa
    46681 tataaaaata agaatagatt aaatatttaa taaataaata ttatgcaatt agtattagca
    46741 gctaaatata ttggagcagg tatctcaaca attggtttat taggagcagg tattggtatt
    46801 gctatcgtat tcgcagcttt aattaatggt gtatcaagaa acccatcaat taaagaccta
    46861 gtattcccta tggctatttt aggtttcgcc ttatcagaag ctacaggttt attctgttta
    46921 atggtttcat tcttattatt attcggtgta taatatatat aatatattat aaataaataa
    46981 aaaataatga aattaataaa aaaataaaat aaaataaaat ctcatttgat taaattaata
    47041 acattcttat aattatataa ttattataaa atatataaat attataataa taataatata
    47101 tataaattat aataaaaaat aataataata tataatatac ctttttttta atatattaat
    47161 atataaataa ataaataatg gataatatat aattactttt tttatattat taataataat
    47221 aatttataaa tattgttata ataaacattt atataaataa atataaatta ccataataag
    47281 atatattatt tattaataat aaaaatattt attaataaat aagaaatata tatattatga
    47341 taatatttat taataaataa taaattcttt atatataaat atattaaata tatttaattg
    47401 aacacaatat aatttttatt gtattattca tttaataata ttaatattaa tattaatata
    47461 atattagtga acatctcctt tcggggttcc ggctcccgtg gccgggcccc ggaactatta
    47521 atatttaata aaatatatat aatttataat tttcatataa ttaatataat aattaggttt
    47581 ataaataaat tataatatat tataacaata taataaaata tattataaat ctatctatct
    47641 atctatataa tatataaatt tatatataca ttaataatat ttaattataa ttatttaaat
    47701 atttaattta ttaatattcc ccgcgggcgc caatccggtt gttcaccgga ttggtcccgc
    47761 ggggtttata ttatttaaat attaaatatt aaataataat ttatattata ttaataaata
    47821 taataaatta aaaatatatg attaattata taataataat aataattatt ttaatattat
    47881 aatttataaa attaattata ttaattatat taattcttat tatataataa ttattaataa
    47941 taatttattt taagaaagga gtgagggacc ccctcccgtt agggaggggg accgaacccc
    48001 gaaggagaaa ataaattaat aaaagtttaa aagttcttat attaataatt atataatatt
    48061 atattaaaga tttttataat atatatatat aatatattta tagttccggg gcccggccac
    48121 gggagccgga accccgaaag gagtttattt aatatttata tttatattaa tatttatatt
    48181 tatatttata ttcctcttaa ggatggttga ctgagtggtt taaagtgtga tatttgagct
    48241 atcattagtc tttattggct acgtaggttc aaatcctaca tcatccgtaa taatacatat
    48301 atataataat aattttaata ttattcctat aaaaataaaa taaataaata aataataata
    48361 attaattaat taattaattt taataaatat aaaatatata aaaataataa taataataat
    48421 tattatttta ataatattat ttatataata gtccggtccg acccttttta ttcttaagaa
    48481 gggattttat tttattaatt aataataata tattaaaaat tataaataat taataattct
    48541 ttatatttat atatatatat atatatttat atatttatat atatatttta ataatattat
    48601 gatatatttt attttaataa tatttttatt tttatatata aaattataat attttatttt
    48661 ataaattatt tatatataaa ttattaataa taattatttt tttttatttg ggatttatat
    48721 tattattata aagaatataa tgttattaat aactgcaaaa aatatctaat atattattat
    48781 ttataataat aaataatatt ataataagga tgcatattat atatatatat atatttctat
    48841 ttatattaat attaatatta atatgtatat ataatagata aaaagtaaaa ataaaaaata
    48901 atgaaattaa aattattaaa tataatttta tcaataataa ataaacttaa taataataat
    48961 aatattatta ttaataatct attagattca ttaataaata agaaattatt attaaagaat
    49021 atattattag atataaataa taaaaaaata aataatataa aaagaatatt aaataataat
    49081 aatataaacc ccgcgggcgc caatccggtt gttcaccgga ttggtcccgc ggggaatatt
    49141 aataataaat tacaacattt aaataatata aataattgaa atctacaaat ttataattat
    49201 aataaaaata tagaaattat aaatactata aatgataaat taattaataa attattatat
    49261 aaaataataa ctttaaaatt aaataatata aatattaata aaattattat aagtaaactt
    49321 attaatcaac atagtttaaa taaattaaat attaaatttt attattataa taatgatatt
    49381 aataataata ataataataa taataataat tattatataa atataataaa taaattaata
    49441 aatattataa ataataatat aaataataat ttatgtaata ttttaagtta ttattataaa
    49501 aaaaaagtaa ctattgaacc tattaaatta tcatatattt atttaaatag tgatattttt
    49561 agtaaatata ttagtttaaa tgatatagat aaatataata atggtatctt aactaattat
    49621 caacgtatat taaataatat tatgcctaaa ttaaatgatc ataatatttc tataaattat
    49681 attaataata ttaataatat taataataat aaatataata atataattaa tttattaaat
    49741 aataataata atattaataa taataataat tataataata ataataataa ttatattggt
    49801 aatattaata atatttataa taatataact attgataata ttcctataga tattttaata
    49861 tataaatatt tagttggttg atctattaaa tttaaaggta gattaagtaa taataatggt
    49921 agaactagta cacttaattt attaaatggt acttttaata ataaaaaata tttatgaagt
    49981 aatattaata ataattataa attaaattat atcccttcta atcataattt atataataat
    50041 tctaatatta ataaaaatgg taaatataat attaaagtta aattaaactt tatttaatat
    50101 atatattaat agttccgggg cccggccacg ggagccggaa ccccgaaagg agaaataaaa
    50161 taaatataat aaataaaata aataaataaa taatatatat atatatataa atatataaaa
    50221 taatatttac tttttatata tatataatta tatataaata aaatataata taatatcata
    50281 taattatata aaaataaaat tataatttat ttatattaaa aatattaatt aattaatttt
    50341 tttatataat tattataata ataatttaat taaaaataaa tatcaaataa aattataaat
    50401 taatcctact tttggatcct atttatattt tattattata aataattatt attgatagtt
    50461 aattaaataa aaatatatat atatattact ccttcggggt ccgccccgca gggggcgggc
    50521 cggactatta taattattat taatatatta attattaaat tatataaacc gcccccgcgg
    50581 gggcggttag ttatttatat taatatattt tatattaata tataatactc ttttttctat
    50641 tatattttaa tatataatat taaaaaaaat aaataaaata atattcttaa tttttattct
    50701 ttatcttctt taaccaaact ccttcggggt tcggtccccc tcccattagg ttagggaggg
    50761 ggtccctcac tccttcgggg tccgcccccc cccgcggggg cgggccggac tattttaaat
    50821 tttaatttaa attttataaa tataatattt aattataaat ttaataataa tatataaaaa
    50881 atatatatat ggttaatata tataaagatt ataatctttt tattaaataa aggaaaattt
    50941 attatataat ttttctctat agttatatat ttaaaactta tttttttttt tttataaata
    51001 ataattataa taaataatat taattattta ttatataatt aattggcccc catgctgggt
    51061 tccggaactc ctccttctcg cgaggttaac acctattata taactataac tataactata
    51121 actataatta taattataac tataactata aatattcatt ttaataataa taataataat
    51181 aatattaata taaatagtcg aagaatatat ttatttattt taatataaat aaaaagtttc
    51241 aattaatttg aatttggaat taaattatta cttcatatgg ggttatggat ttcgttcgga
    51301 actcctccct cctacctcta tttattaatc ataaatcata aattattatt aattaataat
    51361 aataatttac tcgaggttca tacctatttt aatattaata ttaatattga taaaatatat
    51421 attcactaaa aagtatataa tttactcaat ttatactata attttatatt tttttattat
    51481 aatttaatta tttcaaataa agtaattata ataatatata tcctttatta aatatatatt
    51541 aattaatata tatataaaaa gtaaatatta ttaattgtat ataattataa ataattaata
    51601 tttattaaaa tatatataat ttataatcct catataatta atataataaa taatataaca
    51661 caatgtaatt taatttaatt acataataaa tttattatta ttataattat tatttattta
    51721 tttatttatt attataaatt ataaatatta ttataattaa aatcaattat taattattaa
    51781 tgataaataa ttaatgataa attatcaata accaattaga ttatttatcg atatttaatt
    51841 atattatatt atattatatt atatatatat atatatatta tattataaaa tttatttata
    51901 aatatttgtt tatttattta tttattgaat aacaatagaa ttaaatattg tcaataaata
    51961 ataaataatg tttaatatat attatattat attaatatta atattattat tatttttttt
    52021 attatattaa tataatttat aaaaatataa aattattatt tttattataa tttatatata
    52081 tataatatat atatttatta aaatatttta agaaaggaga aaaataatta aattaaatta
    52141 aattaaatta tttattatta ttattattat ttatataata atatattatt taaatattta
    52201 tatattattt ttatattaat atttatagat ggggggtccc tattattatt gaaaataata
    52261 attattaatg gaccccagat agcttcttgt ttatcattta tatatatata tatattatta
    52321 attattttat tctcctttcg gggttccggc tcccgtggcc gggccccgga actttataat
    52381 attattatta attatttaat taatattata atcatataat ttaatatttt atttaatttt
    52441 attaaaattt aatatatata tttttattat tatttaatta atttataaat ataaaatatt
    52501 cttaatatta aaaataaata aataataaag tttataaatc atatattata attatttatt
    52561 atttttatat tatattaata aaatattatt attataaaaa aaaatagaaa ttttataata
    52621 tttttatata tttttaatta ttattattaa tatttattaa aggaaatata aaaaccgaag
    52681 gaatattata attataatta taattattat tatatttaat ttattattat aataataatt
    52741 atagtctgcc ccctctttat ctttatttta aagttccggg gcccggctac gggagccgga
    52801 accccgaaag gagaaggata tttaataatt tataatattt aattcatata tatatatata
    52861 tattttattt tttatatata tatttaatat attatattta tatttatatt attattatat
    52921 ttatattata ttatttaatt attttttaat aatatattat taatatttta ccttttgata
    52981 aataaaaatt tattaaaaat tttataataa gtattaaaat atcataaaag tataatattt
    53041 atataaaatg tataaattta taatcttcta attaaattaa attaaataaa taaaataaaa
    53101 taaattaaac tccttttgag attcacacct attttattaa aaataggtat tcacttaatt
    53161 aaattaaatt aaattaaatt aaattatgga taatttattt aataaatata tatattaatt
    53221 ataaaataat agtccggccc gccccgcggg gcggaccccg aaagagtctg ccccttttta
    53281 tttaatattt aatatttaat atttaatatt taatatttaa tatttaaaga aggatatatt
    53341 tataatttat cataatatta tttaataaga aattattaat taattaatta attaatttat
    53401 ttattgttta tatttattaa tattaatata ataaaaatgt aaaatactta atattattaa
    53461 tattattata tataatatat ataataatat attatattta tatctccttt attccttttt
    53521 cccccgatgg ggacttatta tattatatta ttatatattt cttcgataac tttatatata
    53581 ttttattttt ataaaaaaat atttatatat tattatttac aataataatt attaatagtc
    53641 cggcccgtcc cgcggggggg aaccgaagga gtgcgggacc ccgtgggaac cgcatccctt
    53701 tttattttta attaagaagg agtgagggac cccgtgggga ccgaaccccg aaggagtctt
    53761 ttttctattt attaataata actataaatt atatttaaaa taataattta cttgttataa
    53821 tcttaatgtt ccggggcccg gccacgggag ccggaacccc gaaaggagaa gtatataaat
    53881 atttacttgt tataatttat tatatattta taacctcctt cttaaaatta tctttacttt
    53941 ataataaaaa ttaatataat ataatctgat aataatcgaa ttttattata tttaatttaa
    54001 ttaataatag acaattatta ttattatttt acttattaat attaatttag atttatatat
    54061 ataaatatta ttaattttat attaattttt tattaattat ttatttttat attcatattt
    54121 tttattaata ttatttttat taataacttt ttaaataatt ataaactata tattatttat
    54181 atttatattt ataataaatg aaacaattat aataaaaatt acaattacaa ttatattata
    54241 attatgatta caatagggtt aaacattacc tgtgaacaac tggtaatgtt taacccgtat
    54301 tattatttat tatattatat atatattaaa atattaatat taatattaat attatattat
    54361 attatattat attatattat attatattat attatattta taattatatt atattatata
    54421 atttatatac ttttataatt cttattatta tttatttatt atttatttat tattatttaa
    54481 atatattatt attatatatt aataatatat atattatttt atatatttta tttaatataa
    54541 attatttata tttttatatt ttattatgag ggggggtccc aattattatt ttcaataata
    54601 atttatcatg ggacccggat atcttcttgt ttatcattta ttattcttat tatttggttt
    54661 ttatttaata tttataattt attttataca atttattata ttgtttatac cttattatta
    54721 ttatataata tattatatta ttataataat ttaattaatt atattattaa atattaacta
    54781 atgtgtgctc tatatatatt attcattcta gtttctaatc acccaccccc tccccctatt
    54841 acttatatat ctagaaataa aaatacataa catatatttt aaatatatat atataattat
    54901 ataataatta ttatatataa aatatatata tatataatat atatttataa aataataata
    54961 ataaatatta ttactccatt agaggttttg gtcccatatc aggaaccgaa actataataa
    55021 tatataatat tataataaag atattcttat ttataatata ttattaaata aattaataat
    55081 aattataata tatatatata atatattata atatatttat tcgagaactt tttatttatt
    55141 ataaaataaa atattttatt tattatttag ttttttttta ttaaacattt tataaaaata
    55201 taaatgttaa taatattatg attaataagt aataataaat ttatttattt ttattaatta
    55261 cttcttcgag gtattagtat cagtatcagt atcagtatcg taaaaaacgg gtgactaaaa
    55321 tatatatata tataaaatta taaataaaaa tattataata attttaaata aataaatatc
    55381 aatatattat tattatttat attataataa atattatcta ataatagtcc ggcccgcccc
    55441 cgcggggcgg accccgaagg agtccgaacc ccttttttat ttaattttat ttaaagaagg
    55501 agtgagggac ccctcccgtt agggaggggg accgaacccc gaaggagata attagatata
    55561 attatatttt attttatata aattatataa tattatataa taataattat ataataagtt
    55621 aataataatt atataataag ttaataataa tcatatctcc tttataaatg aacttttatt
    55681 aaatatattt tattaaatat taaatatatt ttttataata ttaaatatat tttattaaaa
    55741 tatttaatat attttattaa atattaaata tattttatta aatattaaat ataaataaag
    55801 gtttatatta taattcatta tttatatctt ctttataaat taatattcgt attagatcct
    55861 tatttaattt ataatccttt aaaaaacttt taataaatat aatataatat atatataatt
    55921 tttattattt ttatattatt tttattattt aaatatatta tatatttcat tataataatt
    55981 atttaaaaag ttatttaata aataatctga tattatattt tataattaat tttatttatt
    56041 ttatttatta tatatattat tatatataat taaaattata attacaatta taactataat
    56101 taaattaaat taaattaaat tggattaaat taaattaaat tgggcgccaa gccggttgtt
    56161 caccgacttg gtcccaatat aatatgagat aatataatat actatatgat ataacataaa
    56221 tataatatat tatatgatat aacataaata taatatactc cttcggggtc cgcccccgcg
    56281 tgggcggacc ggactatatg aatatattat tattataatt ataaaattat aataaataaa
    56341 taaaatttct ttaataatta ttaattaata ttattaattt atttacaaat attttattaa
    56401 tttttatttt tattaaatat aaatatataa atatatatat atttatttat aatattattt
    56461 atatttatta tatattatta ttaaatatat ttttattata tatcattaaa tattaatatg
    56521 ttattatagt ggtgggggtc ccaattatta ttttcaataa taattattat tgggaccccg
    56581 gatatcttct tgttaatcaa ttattatatt atttaattta ttttatttct tatttataat
    56641 ttatattata taatttatta tattgttaat actccttcgg ggtccccgcc ggggcgggga
    56701 cttttattta tattattaat tatattatat tattataata tatttaattg attatattat
    56761 aaaattataa ctaatgtatg ctttgtattt attgaatagt ttggttctta tcacccaccc
    56821 cctcccccta ttacttctcc gaggtcccgg tttcgtaaga aaccgggact tatatatttg
    56881 gtaattaaaa atataactta tataaatatt taataaatat atattaaata tattattatt
    56941 aataatttat tattatataa aaaaataata aatattatta atgatttaaa ttatataaat
    57001 attaattatt aaataaataa ttatactttc tcctttcggg gttccggctc ccgtggccgg
    57061 gccccggaac tttaaaataa tatatatata tataaaagta ttttataata attagtttaa
    57121 ttattattct ttttttttat taaatataaa atcattttag gttattaatt tttatttatt
    57181 aaaaataaat tttataatta atatttctcc tttcttaaaa taaataatat tattattata
    57241 attattaatt aatgaatact cttctctttt ggggttcggt ccaccctccc gtatacttac
    57301 gggagggggg tccctcactc cttttgagac tttaatttta taaatataaa tataaatata
    57361 ataagatgtt aactctttta taaataaata ataaatataa ttctattttt aataataata
    57421 tataatattt ttataataaa atatataaat aataatattt atatatatat atatactttt
    57481 ttttatataa gaataatata tatagttcac attggaggcg agtaaaagga gataagaaat
    57541 ataatataat ataataataa aaatataatg aataataata ataaaaattt atataataac
    57601 aaaatagtcc gaccgaagga gatgagatta ttaatattat taaataataa aatgtattaa
    57661 ttataaaata taaaacctat aaataattta taatataatt tatattatga taataataat
    57721 atatatatta taatatttta tatatatatt tattatattt atatttatat aaaaaagtga
    57781 tattgattaa ttaattaatt tataattaat aattattaat atagtccggc ccgcccccgc
    57841 ggggcggacc ccgaaggagt ccggccgaag gagtttatta tattatatta aataagattt
    57901 ataatataat taatatatat tttaataaat ataaaagatt atattatatt ataaaaagta
    57961 tattttatat ttatatttta tttattatta ttattatata tataagtagt aaaaagtaga
    58021 ataatagatt tgaaatattt attatataga tttaaagaga taatcatgga gtataataat
    58081 taaatttaat aaatttaata taactattaa tagaattagg ttactaataa attaataaca
    58141 attaatttta aaacctaaag gtaaaccttt atattaataa tgttattttt tattattttt
    58201 ataataagaa taattattaa taataataaa ctaagtgaac tgaaacatct aagtaactta
    58261 aggataagaa atcaacagag atattatgag tattggtgag agaaaataat aaaggtctaa
    58321 taagtattat gtgaaaaaaa tgtaagaaaa taggataaca aattctaaga ctaaatacta
    58381 ttaataagta tagtaagtac cgtaagggaa agtatgaaaa tgattatttt ataagcaatc
    58441 atgaatatat tatattatat taatgatgta ccttttgtat aatgggtcag caagtaatta
    58501 atattagtaa aacaataagt tataaataaa tagaataata tatatatata aaaaaatata
    58561 ttaaaatatt taattaatat taattgaccc gaaagcaaac gatctaacta tgataagatg
    58621 gataaacgat cgaacaggtt gatgttgcaa tatcatctga ttaattgtgg ttagtagtga
    58681 aagacaaatc tggtttgcag atagctggtt ttctatgaaa tatatgtaag tatagccttt
    58741 ataaataata attattatat aatattatat taatattata taaagaatgg tacagcaatt
    58801 aatatatatt agggaactat taaagtttta ttaataatat taaatctcga aatatttaat
    58861 tatatataat aaagagtcag attatgtgcg ataaggtaaa taatctaaag ggaaacagcc
    58921 cagattaaga tataaagttc ctaataaata ataagtgaaa taaatattaa aatattataa
    58981 tataatcagt taatgggttt gacaataacc attttttaat gaacatgtaa caatgcactg
    59041 atttataata aataaaaaaa aataatattt aaaatcaaat atatatatat ttgttaatag
    59101 ataatatacg gatcttaata ataagaatta tttaattcct aatatggaat attatatttt
    59161 tataataaaa atataaatac tgaatatcta aatattatta ttactttttt tttaataata
    59221 ataatatggt aatagaacat ttaatgataa tatatattag ttattaatta atatatgtat
    59281 taattaaata gagaatgctg acatgagtaa cgaaaaaaag gtataaacct tttcacctaa
    59341 aacataaggt ttaactataa aagtacggcc cctaattaaa ttaataagaa tataaatata
    59401 tttaagatgg gataatctat attaataaaa atttatctta aaatatatat attattaata
    59461 attatattaa ttaattaata atatatataa ttatattata tattatatat tttttatata
    59521 atataaacta ataaagatca ggaaataatt aatgtatacc gtaatgtaga ccgactcagg
    59581 tatgtaagta gagaatatga aggtgaatta gataattaaa gggaaggaac tcggcaaaga
    59641 tagctcataa gttagtcaat aaagagtaat aagaacaaag ttgtacaact gtttactaaa
    59701 aacaccgcac tttgcagaaa cgataagttt aagtataagg tgtgaactct gctccatgct
    59761 taatatataa ataaaattat ttaacgataa tttaattaaa tttaggtaaa tagcagcctt
    59821 attatgaggg ttataatgta gcgaaattcc ttggcctata attgaggtcc cgcatgaatg
    59881 acgtaatgat acaacaactg tctccccttt aagctaagtg aaattgaaat cgtagtgaag
    59941 atgctatgta ccttcagcaa gacggaaaga ccctatgcag ctttactgta attagataga
    60001 tcgaattatt gtttattata ttcagcatat taagtaatcc tattattagg taatcgttta
    60061 gatattaatg agatacttat tataatataa tgataattct aatcttataa ataattatta
    60121 ttattattat taataataat aatatgcttt caagcatagt gataaaacat atttatatga
    60181 taatcacttt acttaataga tataattctt aagtaatata taatatatat tttatatata
    60241 ttatatataa tataagagac aatctctaat tggtagtttt gatggggcgt cattatcagc
    60301 aaaagtatct gaataagtcc ataaataaat atataaaatt attgaataaa aaaaaaataa
    60361 tatatattat atatattaat tataaattga aatatgttta tataaattta tatttattga
    60421 atatatttta gtaatagata aaaatatgta cagtaaaatt gtaaggaaaa caataataac
    60481 tttctcctct ctcggtgggg gttcacacct atttttaata ggtgtgaacc cctcttcggg
    60541 gttccggttc cctttcgggt cccggaactt aaataaaaat ggaaagaatt aaattaatat
    60601 aatggtataa ctgtgcgata attgtaacac aaacgagtga aacaagtacg taagtatggc
    60661 ataatgaaca aataacactg attgtaaagg ttattgataa cgaataaaag ttacgctagg
    60721 gataatttac ccccttgtcc cattatattg aaaaatataa ttattcaatt aattatttaa
    60781 ttgaagtaaa ttgggtgaat tgcttagata tccatataga taaaaataat ggacaataag
    60841 cagcgaagct tataacaact ttcatatatg tatatatacg gttataagaa cgttcaacga
    60901 ctagatgatg agtggagtta acaataattc atccacgagc gcccaatgtc gaataaataa
    60961 aatattaaat aaatatcaaa ggatatataa agatttttaa taaatcaaaa aataaaataa
    61021 aatgaaaaat attaaaaaaa atcaagtaat aaatttagga cctaattcta aattattaaa
    61081 agaatataaa tcacaattaa ttgaattaaa tattgaacaa tttgaagcag gtattggttt
    61141 aattttagga gatgcttata ttcgtagtcg tgatgaaggt aaactatatt gtatgcaatt
    61201 tgagtgaaaa aataaggcat acatggatca tgtatgttta ttatatgatc aatgagtatt
    61261 atcacctcct cataaaaaag aaagagttaa tcatttaggt aatttagtaa ttacctgagg
    61321 agctcaaact tttaaacatc aagcttttaa taaattagct aacttattta ttgtaaataa
    61381 taaaaaactt attcctaata atttagttga aaattattta acacctataa gtttagcata
    61441 ttgatttata gatgatggag gtaaatgaga ttataataaa aattctctta ataaaagtat
    61501 tgtattaaat acacaaagtt ttacttttga agaagtagaa tatttagtta aaggtttaag
    61561 aaataaattt caattaaatt gttatgttaa aattaataaa aataaaccaa ttatttatat
    61621 tgattctata agttatttaa ttttttataa tttaattaaa ccttatttaa ttcctcaaat
    61681 gatatataaa ttacctaata ctatttcatc cgaaactttt ttaaaataat attcttattt
    61741 ttattttatg atatatttca taaatattta tttatattaa attttatttg ataatgatat
    61801 agtctgaaca atatagtaat atattgaagt aattatttaa atgtaattac gataacaaaa
    61861 aatttgaaca gggtaatata gcgaaagagt agatattgta agctatgttt gccacctcga
    61921 tgtcgactca acatttcctc ttggttgtaa aagctaagaa gggtttgact gttcgtcaat
    61981 taaaatgtta cgtgagttgg gttaaatacg atgtgaatca gtatggttcc tatctgctga
    62041 aggaaatatt atcaaattaa atctcattat tagtacgcaa ggaccataat gaatcaaccc
    62101 atggtgtatc tattgataat aatataatat atttaataaa aataatactt tattaatata
    62161 ttatctatat tagtttatat tttaattata tattatcata gtagataagc taagttgata
    62221 ataaataaat attgaataca tattaaatat gaagttgttt taataagata attaatctga
    62281 taattttata ctaaaattaa taattatagg ttttatatat tatttataaa taaatatatt
    62341 ataataataa taattattat tattaataaa aaatattaat tataatatta ataaaatact
    62401 aatttatcag ttatctatat aatatctaat ctattattct atatacttat tactcctttt
    62461 taattaaatt aaaaaggggt tcggttcccc ccccccataa gtatgattat aattataatt
    62521 ataatataag ggaggggtcc ctcactcctt atggggtccc ggttggaccg agactcctcc
    62581 cttgcgggat tggttcacac ctttataaat aaataataaa taataaataa aggtgttcac
    62641 taataaatat atatatatat atatatatat tatattataa tattatttaa tacttaatat
    62701 attatatatt ttatatttaa taaataaaaa aaatattaat aaataataat attaataata
    62761 aagaaattat aattaatacc ctctatatat aattctaatt aattaaatta aatatttata
    62821 tataataatc aatatattat taatttaata attattataa tagttccggg gcccggccac
    62881 gggagccgga accccgaaag gagtttataa aagatatatt tttatattat attatattat
    62941 atttaataaa tattaccttt ttttattatt atttttatat attatataat attattaatt
    63001 tttattataa tattatttac ttttttattg gattatttat ttatttattt atttattaat
    63061 taattaatta aatatttatt aattaatata tatattaaat attaatattt cattaaaaaa
    63121 aagagatata tgaataatat attatgttat attatattat ataattatat tatttttata
    63181 atattaataa ttaaaaataa gaacttattt aaaaattata attatgataa taaattaata
    63241 ctttttaatt tataaaaata taaatttctt tacatatata tatatatata ttattattat
    63301 ttatattaat cataatttta atatttataa taaatttata taaaatcaat tataatatta
    63361 tatacttttt atatacttta taatctttat atcttcaccc cccctttttt aataatatat
    63421 tatattaaaa atataataat ttatatgatt tattaatact ttttatataa ttatattatt
    63481 attttttttt atagatgtta tattattttt tataataatt tttttttatt taaataaaat
    63541 ttataactcc ttcttaatta aagataaaag gggttccccc cttaagtata agtataagta
    63601 taagtataag tataagtata agtataagta taagtataag tataagtata gtatacgggg
    63661 ggggggtccc tcactccttc gttaatttat atatattatt aataattatt taatttttat
    63721 tatttattta tatataaaaa tattctaaaa ttattaatat ttataataga ataaatatta
    63781 taaagtataa ttataaataa ttaattattt aaataataat aatatattta ttattatata
    63841 ataaatatat tataaataat agttatatta gcttaattgg tagagcattc gttttgtaat
    63901 cgaaaggttt ggggttcaaa tccctaatat aacaataata ataataaaat attaaaataa
    63961 atataatatt tataaaaaat ttattaattt atataaaaaa tatatatata aataataatt
    64021 ataataaaac attttataat caataattta ataaataatc ttcttattat aatattatgt
    64081 ttaaatatta ctctttatga ggtccaacaa actaataaga tataaatata tatatattat
    64141 ataataataa taataataat atattattta atatattatc aagaagataa atataaataa
    64201 tattttaata attttaaata aatctaattt atatattaat aatttaataa tcttaatatt
    64261 tattatcatt atttcatatt tatattatat aaatatttat ttaaataaaa aatattaaag
    64321 agtttatttt atttattata aattatttaa taaaatatat ataataatat atagaataaa
    64381 gatataaata attataagta tataaagtaa taaaggagat gttgttttaa ggttaaacta
    64441 ttagattgca aatctactta ttaagagttc gattctcttc atctcttaaa taaataatat
    64501 aataataaaa tattatagtt ccggggcccg gccacgggag ccggaacccc ggaaggagat
    64561 aaatatatat atatttataa taattatata ataaaggtga atatatttca atggtagaaa
    64621 atacgcttgt ggtgcgttaa atctgagttc gattctcagt attcacccta taaataataa
    64681 taataatata ttttattatt cttaaatttt ttattcttta tattatatat ataatattaa
    64741 tattattact ttttaataac aaaatattat aattaattga tatatatata taccaaatat
    64801 aattaattga aattaaataa taaataaaat atttacttct ttattaaaat tctaattaat
    64861 tgattctttt tattgaatat taaattctat tataacttat taattaatta attaattaat
    64921 tataataata ataatattta ttattaatta ttaaatattt attattatat ataagattta
    64981 attttaaata ttaataaaaa aagaataaaa taaaataaaa tgaataatat ttctttatct
    65041 ctttcgatcg gactccttcg gccggactcc ttcggggtcc gccccgcggg gcgggccgga
    65101 ctatttatta ttataatata atatttaatc aatagattta taatttattt aatgaatatt
    65161 ttataaatat ataaaacaat tcctttttat tattataaat ttttcattat ttatttattt
    65221 atttatttat ttattcaata tataaaaata attataaaaa gattattaaa aataataatt
    65281 taatgataaa tatatattat atatattaat ataaaaataa taaatataaa tatattatgt
    65341 aaatattata taaatttgta tatgtatata ttataataat gttatataag taataatata
    65401 ataaaatatt ttatgtaatt tatatatatt tataattata aaataaaaat attataaata
    65461 ataaaattaa taataataat aattttaata aaataaatta tatatttaat tttattatga
    65521 agtttatact taatataaat tatatttcct ttataaatta ttaatatatc ctttttaatt
    65581 aaataaaata aaaatattat aaatattaat aattaatttt ttatttatat ttatatatat
    65641 attaaagatt aaatatatta ttaatactaa tttataattt attattaata aatagtccgg
    65701 cccgccccct gcggggcgga ccccgaagga gttcgactta aattataatt taataatttt
    65761 tatttattaa tagtttcggg gcccggccac gggagtcgga accccgaaag gagttttatt
    65821 attaatataa aaagagtaag gataataata aattctttta atttattttt aataaaatat
    65881 aattttaaaa tagtttttat agtccggccc gccccgcggg ggggggcgga ccccgaagga
    65941 gttcggtctg gcattaatta taataattat attaatatta ttattattta ttatattata
    66001 atatatttat ttatatttta taatattaat aattatttta tatttaataa atataatata
    66061 tatattattt tttttaataa ctatctaatt aatagctatt ttggtggaat tggtagacac
    66121 gatactctta agatgtatta ctttacagta tgaaggttca agtcctttaa atagcaataa
    66181 atatatataa tatatataat atatataaat gagtcgtaga ctaataggta agttaccaaa
    66241 atttgagttt ggagtttgtt tgttcgaatc aaaccgattc aatattataa tatatatatt
    66301 atttatatat aaatatataa ttatactcct atttttatat taattaatta ataatatatg
    66361 ataatataaa aattattgaa ttattaactc ttattaataa taataataat cataataata
    66421 atatatatat atatagtata tatataaaag ttttattata ttatattata ttatatattt
    66481 atttatatat aattcttatt aattgaaaaa agaataatta ataatcttat taaaaaaata
    66541 aatactttca ttttatttta ttttatttaa tttaattata atatataaat attaaaaaaa
    66601 ggatataagt tttttataag atataatata tatatatatt aaatataaag aagttaatat
    66661 ttatatttta attataaaat gttaatactc ctttggggac ttattaatta aattattaat
    66721 taataataat ttatgattta taaataataa ataaaggaat aagtatcaat taattaatat
    66781 attatattta atattttata tttaatattt aatatttaat attttaagtt ccggggcccg
    66841 gccacgggag ccggaacccc gaaaggagta gtattaatta tggatagtga gggtggattt
    66901 aatccttttg ttatgttatt aattaattaa ttaatttata tatataaaat attttaatta
    66961 atttttatat aaatatatat atatatatat attaataata gtccggcccg ccccgtgggg
    67021 cggaccccaa aggagtaata tatattatgt ataaacaata gagaatattg tttaatggta
    67081 aaacagttgt cttttaagca acccatgctt ggttcaactc cagctattct cataatatta
    67141 tatatatata tttccctttc taaaaataat aataattata tataataata atataattat
    67201 atatatatat attataataa taataataat aataataata aataataata attattttta
    67261 ttaataatat taatatatta taattattaa taaatattaa taaaaatagc tctcttagct
    67321 taatggttaa agcataatac ttctaatatt aatattccat gttcaaatca tggagagagt
    67381 aattatatta tattaataat ccccccccca tttttaatta aattaagaag tttaatttac
    67441 tatttaataa taaatgaaat aataataata gatataagtt aattggtaaa ctggatgtct
    67501 tccaaacatt gaatgcgagt tcgattctcg ctatctataa ttaatattaa tataaattaa
    67561 tatcctataa ttaattaaat acaaaattat attaaaactt atattatatt atattataat
    67621 attatattat tattatataa aaatataata ataataatat ttaattttat ttaataataa
    67681 tattttatat aataaaataa tcatatttat aatatttaat attaataata atttattata
    67741 ataattcttt aatatactta tttattatta ttttaataaa taaatataat tcttataaat
    67801 atattataac aaaatatatt atattttaat taaatacaat attataaata tatatatata
    67861 tataaatatt tatataaaaa aaaaataaaa atattttaat aattattctt tataaataaa
    67921 taatgataat aataatttat aataatctcc ttgtggggtt ccggctcccg tggccgggcc
    67981 ccggaactat aatatatttt aatatatttt ttattactcc tcctttgggg tccgccccgc
    68041 gggggcgggg cggactataa taatttttta ttgataaaaa agtatatata atataattaa
    68101 tatatttctt tttatataaa ttataaatat tattttataa taaaaaaagt atatataata
    68161 ttatatattt aataaataat ataataataa tataaataaa tatatatata ttattaatat
    68221 attaaatttt ataataataa ttataataat agtagtaggt ataaatttta ataaagagtt
    68281 ttattccaat ggagtaataa taataataat aataaaataa aggatctgta gcttaatagt
    68341 aaagtaccat tttgtcataa tggaggatgt cagtgcaaat ctgattagat tcgtatattt
    68401 atacttaata taaaaaaaat aaataataat cttttttatt attatattta ttaataataa
    68461 attattttgt tattattatt aatttatatt aatattttat ataaattatt tatttaatct
    68521 ttcattatat atttaatata ttattaatat taattaatat tttataataa ataaataaaa
    68581 taaaataaat attttaatat aatactcctt cggggttcgg tccccctccc attagtatag
    68641 tatagggagg ggtccctcac tccttcgggg tccccgccgg ggcggggact tatttttata
    68701 tttattaata ataattaatt tttatataaa tttattattt cttacaatat atttattact
    68761 attatttttt aataatctta tatataatat ataaaatata tatatattat atatatatat
    68821 aaatataata tatattatta taaatattta taatcttatt aattaattag attatattat
    68881 attatattag atcatattat attatattat attatattat attattatta ttaatatttt
    68941 tatttttatt ttatatttaa tagtaaaaaa tcataatttt ataatttatt aattattata
    69001 taatttcatt aatatatttc ttctttttat ttatttattt attacttatt aatagttccg
    69061 gggcccggcc acgggagccg gaaccccgaa aggaaaataa tataaaaaat aattataatt
    69121 tattataatt tattaattta ttaatttatt aatttattta ttaatttatt aatttattta
    69181 ttattatatt ttttttaata aaggaaaatt aactataggt aaagtggatt atttgctaag
    69241 taattgaatt gtaaattctt atgagttcga atctcatatt ttccgtatat atctttaatt
    69301 taatggtaaa atattagaat acgaatctaa ttatataggt tcaaatccta taagatatta
    69361 tattatatta tataatatta tatattaata aatattatta attaatttat ttatttattt
    69421 attattaaat aaaaatattt aatagttccg gggcccggcc acgggagccg gaaccccgaa
    69481 aggagaataa tataaaatat tataattatt tatatattaa ttattaatta tttattattt
    69541 attatataaa aagtatataa ttttatattt taatataggg ttaattaatt aattattaat
    69601 tttttataat aagataataa tatattaaaa acttattata aatttataaa ataatattta
    69661 tttactttga tattattttt aatctttcat taatatatat tttattataa gtaataatat
    69721 agtttaattt aattaatata aataaattac ataagaataa tattataata atattatata
    69781 ttatataaag aaataataat ttatattttt atttttttta taaataatat aaatataaat
    69841 ataatggggt tatagttaaa tttggtagaa cgactgcgtt gcatgcattt aatatgagtt
    69901 caagtctcat taactccaat aattatatta tataatatat atattaataa attatatata
    69961 tatatatata tataaatatt aaataaatat tatattaata aataatataa attatctaat
    70021 cgaaggagat atttataata taatataaat attttaataa attaataaat attatattaa
    70081 taaataatta ataaatatat aaattataat aaattttaat attattatat aaattaatta
    70141 aatataataa ttaatgaaat agaaactata attcaattgg ttagaatagt attttgataa
    70201 ggtacaaata taggttcaat ccctgttagt ttcatattat atatcattaa tatataaaat
    70261 ataaatatat atattataat aataataata ataaatataa atataattat atatatatat
    70321 atatataaat aaataattat ttaatttata ataaatatat atagttcccg cgaagcggga
    70381 accccataag gagttttatt attaattata tttaataaat attaattatt aattttatat
    70441 ttataaataa atttattact ccttcttaat taagaataaa aagggatgcg gttcccatgg
    70501 ggtcccgcac tccttcgggg tccgccccct cccctgcggg aggggagcgg actattttat
    70561 taaaaatatt ataattaaat aataatataa ataatttata atataataat atatacttat
    70621 aaataatatt taaatcttat tattaattta taaatcataa attattatta ataaatatct
    70681 cttttagata agataaattg aacttatatt tatattatat atatatagat ataaatctta
    70741 aatagagtaa atatattata ataattatat aaatatatat atattatatt aagataataa
    70801 tatatatata tattaatata taaggaggga ttttcaatgt tggtagttgg agttgagctg
    70861 taaactcaat gacttaggtc ttcataggtt caattcctat tcccttcata atttattatt
    70921 aattatatat tattataaat caaatccatt gaaattaata taatccaatg aataattaat
    70981 ttaatacata atttaatata taaaattata tatatatata ctttataaaa aaaaaaatta
    71041 tataataatt atattaatat atttatatat ataaataaat aaataaataa taataattat
    71101 aattataatt ataattaatt aattaataaa taaataataa tttatattat ctttataata
    71161 tatatatata cttttataaa aaaaatatat aaataattct aaaatgtata tttctccttt
    71221 cggggttccg gctcccgtgg ccgggccccg gaactattaa taaaattaat aataaaataa
    71281 ttattatctg tatttaataa atttaattat agagttatat ttctatatat ttatatattt
    71341 atttatttat tctccttccg gaactaataa aatatataaa ataagggttt ttatttattt
    71401 aattaatata tatttattct tttatataat atgtccttat agcttatcgg ttaaagcatc
    71461 tcactgttaa tgagaataga tgggttcaat tcctattaag gacgataata atatatatat
    71521 attttaattt atatatcata tatatatata tattaaagaa aataatataa aaagtatgta
    71581 ttaataataa taataaataa ataataataa ataattttat tatattatat tatattatat
    71641 ttattgatat atttattgat atttattaat ttaagattat tcattaaata tataattatt
    71701 aataatttaa tatattttat aatttttatt atattttatg taagaagaaa ctattttata
    71761 tattatatat atatatataa tttttataaa atgataaatt ttatattata aatattatta
    71821 aaatattttt ataaatattt aaattattta taaaaaggta tataataata attattaata
    71881 ttatattata ttatatattt atttatatta tatataataa tatatttata tatatattaa
    71941 ttaataaatt aaataagtat ctatatttta tattatatta tattatttta ttttattaat
    72001 tccggaagga gaataaaaag tattctaaag aaattatata tttattattt ttattaatat
    72061 gttataaatt aataaaaaat aaatatgtat atataaatta tatttattat gtttaattat
    72121 ttataattta ttataatata tagtataaga tatcttattt atatttatat ataataaaga
    72181 atattattaa actaacacct atattatata tattatatta tataatatta tatatatatt
    72241 aattactaag aataaattta taattagata atatttatat ttatttattt atttaattaa
    72301 caaatatatt aatattttta attaattaat aataccttta tatatatata tatatatata
    72361 ttaattttaa ttatataatt atctttttta ttaataatta taaatatatt atatatttta
    72421 tataataaga ttataatttt ataattattt tattttttat taaaaattat tattattata
    72481 attattatat tataattata aattattaaa gaatatattt attaatattt taataattaa
    72541 tatcttttat ttatatttat aaaataaggt ataaatattg ataataaaga gtaaatattg
    72601 tattaattat aataataatt ataattaagg agcttgtata gtttaattgg ttaaaacatt
    72661 tgtctcataa ataaataatg taaggttcaa ttccttctac aagtaataat gattataata
    72721 tttatatata ttaaaataat attaataaat aattactcct cctagcagga ttcacatctc
    72781 cttcggccgg actccttcgg ggtccgcccc gcgggggcgg gccggactat tttattatta
    72841 ttaaatagat gttcattaaa taattataaa tataatttat cttttaaata tatatatata
    72901 atataatatt taaatatata ttataaataa ataaataaat aattaattaa taaaaacata
    72961 taatgtatat ttatctataa aaaatattaa ttaaattaat atattattac agttccgggg
    73021 gccggccacg ggagccggaa ccccgaagga gataaataaa taaataaata taaataattc
    73081 ttcttcttta aaattaaata aaataaaata aaaagggggg cggactcctt cggggtcccg
    73141 cccccctccg cggggcggac tattttattt ttaaatatat attatattaa taatataaat
    73201 ataagtcccc gccccggcgg ggaccccgaa ggagtataaa taaaaattaa taatatatta
    73261 tatatatatt atattaataa taataataat aataataata ataaataata actccttgct
    73321 tcataccttt ataaataagg taatcactaa tatattataa taataaaaat tatatatatt
    73381 atatataatc taaatattat atattttaat aaatattaat atatatgata tgaatattat
    73441 tagtttttgg gaagcgggaa tcccgtaagg agtgagggac ccctccctaa cgggaggagg
    73501 accgaaggag ttttagtatt tttttttttt taataaaata tatatttata tgattaataa
    73561 tattatatat attatttata aaaataatat ataattttaa ttatttttaa taaaaaaagg
    73621 tggggttgat aatataatat aatatttttt attttaattt ataatatata ataataaatt
    73681 ataaataaat tttaattaaa agtagtatta acatattata aatagacaaa agagtctaaa
    73741 ggttaagatt tattaaaatg ttagatttat taagattaca attaacaaca ttcattatga
    73801 atgatgtacc aacaccttat gcatgttatt ttcaggattc agcaacacca aatcaagaag
    73861 gtattttaga attacatgat aatattatgt tttatttatt agttatttta ggtttagtat
    73921 cttgaatgtt atatacaatt gttataacat attcaaaaaa tcctattgca tataaatata
    73981 ttaaacatgg acaaactatt gaagttattt gaacaatttt tccagctgta attttattaa
    74041 ttattgcttt tccttcattt attttattat atttatgtga tgaagttatt tcaccagcta
    74101 taactattaa agctattgga tatcaatgat attgaaaata tgaatattca gattttatta
    74161 atgatagtgg tgaaactgtt gaatttgaat catatgttat tcctgatgaa ttattagaag
    74221 aaggtcaatt aagattatta gatactgata cttctatagt tgtacctgta gatacacata
    74281 ttagattcgt tgtaacagct gctgatgtta ttcatgattt tgctattcca agtttaggta
    74341 ttaaagttga tgctactcct ggtagattaa atcaagtttc tgctttaatt caaagagaag
    74401 gtgtcttcta tggagcatgt tctgagttgt gtgggacagg tcatgcaaat atgccaatta
    74461 agatcgaagc agtatcatta cctaaatttt tggaatgatt aaatgaacaa taattaatat
    74521 ttacttatta ttaatatttt taattattaa aaataataat aataataata attataataa
    74581 tattcttaaa tataataaag atatagattt atattctatt caatcacctt atattaaaaa
    74641 tataaatatt attaaaagag gttatcatac ttctttaaat aataaattaa ttattgttca
    74701 aaaagataat aaaaataata ataagaataa tttagaaata gataattttt ataaatgatt
    74761 agtaggattt acagatggag atggtagttt ttatattaaa ttaaatgata aaaaatattt
    74821 aagatttttt tatggtttta gaatacatat tgatgataaa gcatgtttag aaaagattag
    74881 aaatatatta aatatacctt ctaattttga agaactactt aaaacaatta tattagtaaa
    74941 ttcacaaaag aaatggttat attctaatat tgtaactatt tttgataagt atccttgttt
    75001 aacaattaaa tattatagtt attataaatg aaaaatagct ataattaata atttaaatgg
    75061 tatatcttat aataataaag atttattaaa tattaaaaat acaattaata attatgaagt
    75121 tatacctaat ttaaaaattc catatgataa aataaatgat tattgaattt taggttttat
    75181 tgaagctgaa ggttcatttg atctatctcc aaaacgtaat atttgtggtt ttaatgtttc
    75241 acaacataaa cgtagtatta atacattaaa agctattaaa tcttatgtat taaataattg
    75301 aaaaccaatt gataatacac cattattaat taaaaataaa ttattaaaag attgagattc
    75361 atctattaaa ttaactaaac ctgataaaaa tggagttatt aaattagaat ttaatagaat
    75421 agatttttta tattatgtta ttttacctaa attatattca ttaaaatgat atagtcgtaa
    75481 agaaattgat ttccaattat gaaaaacact tatagaaatc tatataaaag gtttacataa
    75541 tacacttaaa ggttctaatt tattaaaatt aattaataat aatattaata aaaaaagata
    75601 ttattctaat tataatattt ctcctttcgg ggttccggct cccgtggccg ggccccggaa
    75661 ctaaaaatat tattgatgat gtattaaata taaatcttat ctataattat aaattaccat
    75721 atcgtataaa tagtgatatt caacgtttaa attctataaa taataataat actaaattta
    75781 ttaatgttgg agtatttgtt tatgatttaa ataatacatt aattataaca tttactggtt
    75841 atagaccagc agctctttac tttaattgtt ctccttttcg gggtcccgac tggggccggg
    75901 actaaacatg aaattgctaa atatattaaa aatggtaatg tatttataaa taaatatatt
    75961 ttaaaaaata ttttattaga ttaattatta tttttacttc ttcttaaaat taaaaaagga
    76021 gactttttta tatttatata aattatatat aaattattct tttattataa atatataaaa
    76081 ttattttctt ttaattattt ttataattaa ttaattcttc atggctatag ccataacttt
    76141 taataatatt cttttattct ttattattat atatatatat atttattatt tattattata
    76201 gaatttatat ttataaaaat attaatattt tatttaaaat aaataatgat taatttataa
    76261 aatatatatt aattaagttt cgggtcccgg ctacgggacc cggaaccccc gagaggagtt
    76321 attatattta taattaaatc tttaaataat atatcttaaa ttattatatt gatattaata
    76381 ttatattgat attaatatta aatatatatt taatatttag cttattattt tataaaatta
    76441 tatttatata ttataatata attaaatata ttataaattt aataatttaa taaaaatatt
    76501 ctttttataa ttattataat aattaaataa ataataataa taagaataat taatgtataa
    76561 tttttttata aatattatat atttttatat taatagttcc ggggcccggc cacgggagcc
    76621 ggaaccccga aaggagaaat attaataaaa taaaataaaa ttataatata attaaattat
    76681 aagaattata tttactcctt ttataattta tatttataat ataatataat ataaaataaa
    76741 tataatataa tataaaataa atataatgta ataggtattc actcctcttt ggggttccga
    76801 tcccccatac ggatacggat acggatacga atacggatac ggatacggat acggggggcc
    76861 gtcccccaga acttaatatt atatcttaaa taattaatat aaatataata tattatttaa
    76921 taataataat aaataaataa ataaataaat aaataaatta aataaataat aatattatta
    76981 taattacttt ttaataaata atattaatat aatattatat tagtattata aatagacttt
    77041 ttattatttt atatataata tagtccggcc cgcccccgcg gggcggaccc cgaaggagta
    77101 atatattata taattattat ttttaattat aaataaaata taattattat ttattatata
    77161 atttatataa atatatatat atatttatta tatatataaa tataaatata aatataataa
    77221 ttaataatat taaagtttta tatatattaa tatattataa aaggtttata tatatatata
    77281 ataagataag taataaatta attaattaat aatataaaaa tatatattat atattatgtt
    77341 ttatttatat atatatatat attatgtatt attatataaa tatatatata tattatatta
    77401 taagtaataa taagtattat attatatata gcttttatag cttagtggta aagcgataaa
    77461 ttgaagattt atttacatgt agttcgattc tcattaaggg caataataat aatatattaa
    77521 ttaataatta atttataata aatatattat aataattaat atatatatat ataatatatt
    77581 taatacaaag aaaatatata ttatatctct tatttattta tttattaata ttttaataaa
    77641 tataatatta taaaaaaaag tttatatatt tagttccggg gcccggccac gggagccgga
    77701 accccggtag gagaaatata aatataaata taaatataat ataagtttgg tattcattta
    77761 attatattat ttaattaaaa atattctaaa taagaataaa tatcaataaa ggagttataa
    77821 atatatatat atattaatat atatataaaa atatatatta ttattagttc ccgctttgcg
    77881 ggaaccccgt aaggagtgag ggaccccatg ggaaccgaac ccctatttaa gaaggagttt
    77941 tattataata aaatttatat atatttaata tataattata aaaatattat ataataaata
    78001 ataaataatt attaataata aataaatata ataataatat tataataaat ttataaatga
    78061 ttataataaa tttatattaa ttttttattt tgtaaatact aagatttgaa cttagataat
    78121 atgcacctaa aaacatacat tttaccatta aattatattt accttattaa ttatataaaa
    78181 tttattaaat atataatata ttaattatat aaaaattatt aaataaatat ataatatatt
    78241 atatataatt tataatatat atattataaa tattattata tataaaatat aatatactac
    78301 ttataaaaat atatatatat atataaatat atatataaat aaatatttta tatattaaat
    78361 taaataatta ttaataaatt taattataaa gtataatttt caataggaat atttataaga
    78421 ttataataat tatatgaatt attataatta tatatatata aataaataaa ataataatta
    78481 taataattaa taagagtttt ggatatatat ctgtggagta tatattttat aaaggagatt
    78541 agcttaattg gtatagcatt cgttttacac acgaaagatt ataggttcga accctatatt
    78601 tcctaaatct agatataata ttatatctat cttaatataa taatatttat ttattaaata
    78661 aaaaaaaaat aaataatatt aattaatata agattctttt ttaattataa taataaataa
    78721 ataaaaagaa gatattatca atgatttata ttaataataa atataaataa taaaaaatat
    78781 atataatata atataataaa tatatttcct ttaatattaa taaattaata ataataataa
    78841 taataataat aaaatattta aataaattat attcaataca aattaattat ttatattatt
    78901 aataattgaa taaataatcc ggtcgaaaga gatattaatt cgattatatt atttatttaa
    78961 ttatatttaa tttaaatata taaattaata tatatatatt gaattatata taaatttatt
    79021 ttataatttt ataaataata tattattata aatatttaat ataatttata ttattattaa
    79081 ataaaagatt tattaaatta atattattat ttaattttat tatatagttt aagggataat
    79141 attttattaa tatttttttt atttatttat ttaattatat tatatatata atatatatat
    79201 aacaataaat ttatgacaca tttagaaaga agtagacatc aacaacatcc atttcatatg
    79261 gttatgcctt caccatgacc tattgtagta tcatttgcat tattatcatt agcattatca
    79321 ctagcattaa caatgcatgg ttatattggt aatatgaata tggtatattt agcattattt
    79381 gtattattaa caagttctat tttatgattt agagatattg tagctgaagc tacatattta
    79441 ggtgatcata ctatagcagt aagaaaaggt attaatttag gtttcttaat gtttgtatta
    79501 tctgaagtat taatctttgc tggtttattc tgagcttatt tccattcagc tatgagtcct
    79561 gatgtactat taggtgcatg ttgaccaccc gtaggtattg aagctgtaca acctaccgaa
    79621 ttacctttat taaatactat tatcttatta tcttctggtg ctactgtaac ttatagtcat
    79681 catgccttaa tcgcaggtaa tagaaataaa gccttatcag gtttattaat tacattctga
    79741 ttaattgtta tttttgttac ttgtcaatat attgaatata ctaatgctgc attcactatc
    79801 tctgatggtg tttatggttc agtattctat gctggtacag gattacattt cttacatatg
    79861 gtaatgttag cagctatgtt aggtgttaat tattgaagaa tgagaaatta tcatttaaca
    79921 gctggacatc atgttggata tgaaacaact attatttatc tacatgtttt agatgttatc
    79981 tgattatttt tatacgtagt cttctactga tgaggagtct aaggctatag aattatatat
    80041 ctaaatgatt aatatatata ttattaataa ttaacaataa ttaatatatt ataatttata
    80101 tatatatatt ttatattatt ataataatat tcttacaaat ataattatta tatattattc
    80161 cttcaaaact cctaacgggg ttcccgcgaa gcgggaacta ataataatat aatcattata
    80221 ctcttttttc atttaccttt tataaagata attaataaat ttatttaata tttataaaaa
    80281 aaaaaatata atattaatat aatataatat aataatgtaa ttatttatat ttttatattc
    80341 cttcgaggtc accgcctcac ctccagcggg acttttttaa tatgatataa tataatataa
    80401 atattattaa tttaactaat atataaattc atatatatat atatattatt aatattattt
    80461 tataaaaaat attttttatt tgattattat taaatattat atagttccgg ggcccggcca
    80521 cgggagccgg aaccccgaaa ggagaaatat taatatatta taaatatact atttatgtaa
    80581 ttattttttg aagtgagcac ctattttata tatattttat atatatttta ttatatttta
    80641 ttaaaaatag gtgtgaacct ccatgagaga ggaatgaata cctattttat aaagtatatt
    80701 ttatattcta tatattataa atatgaacca aaaaaaggag tttaaaattt aattaaattt
    80761 aattaattga atttctttat tattattatc ataattatta aaccctttat taatataata
    80821 atatattatt tattatcaaa atacctaccc tttttataat ttatatcttt aataatataa
    80881 ttaaatataa aatgtttatt aaatattata taaaaataaa aataaaaata tatatatata
    80941 tataaatgat aaataataag gaattcacac ttatataaat ttaaatataa agtcccaaaa
    81001 gaagtattca ttaaataaat tatcattaat taattataat aaacttattt aatattatta
    81061 aagattaatt tataataata attattatta ttattattaa tattaataaa atatataaat
    81121 aattaaatag ttcatatatt aaaaagaatt agaattaaac tttaataagt gtatttaata
    81181 tatagaatat taatagaata tttattctat ttatatatat atttatatat atatatatta
    81241 aataatatta tttatattat attttatata tatattatta atataaaaag tatattatat
    81301 gtattatata tattatatat tatatattta ataatatatt actcctttgg ggtgggtccg
    81361 ccccacgggg cgggccggac tattataatt aataatttta taaagttccg gggcccggcc
    81421 acgggagccg gaaccccgaa aggagaataa ataattatat atcttcttct taattaaatt
    81481 aaattaaatt aaattaaatt aaattaaatt aaattaaaaa ggggttcggt ccccctccct
    81541 aacgggaggg ggtccctcac tcattcaaac tataatttaa tatattatga tattatttat
    81601 aatttataat ataatgtata atattatatt ataaatatta tataaaaata aaatgatata
    81661 tataataata ataataataa taataaaaaa atagaaaaga ataattttta ttattttagt
    81721 atatataaga atttaataag ttatattatt gcggacaccg ttacgcggag tggggactat
    81781 tatattttac ctatatatat taatattatt ataatttcct tctttaaaag aaaaaaggaa
    81841 ttcgagaact tattattata ttaatatatt aataataaat aataataaat aataaaaaag
    81901 taaataatta taaattatat aaaaatataa ttttattatt aagaaaggag tttaaatata
    81961 aaatataata ttatcattaa gttctaataa aggtatataa tgaagatcta ttagaaccta
    82021 aaaagaatat taatatatct attataaaat aataataata aatataaata taaaaataaa
    82081 ttgtaatatt tataaataat aataaaaaat aaataaggaa tatattaatt attaataata
    82141 aataaattat attaaaatat aatattatta ttaaattaaa gaattatatt aaatatattt
    82201 attaaaattt tataaataag ttaatatttt attaaataat atttataaat aataaaaaaa
    82261 aataagtata taattattaa tatattaatt tattatgtta tatatttata tatttcaaat
    82321 atataagtaa tagggggagg gggtgggtga taataaccag aatattaaat aaatacagag
    82381 cacacatttg ttaatattta ataatataat caataaatat attataataa tataatataa
    82441 ttaataatag atataaagta taaacaatat aataaattat ataaaataaa tataaattaa
    82501 aaataataac caaataatta atataataaa tgataaacaa gaagatatcc gggtcccaat
    82561 aataattatt attgaaaata ataattggga cccccacaat agaataaaaa ataaaaagaa
    82621 ttaataatat ataaataata taaaatatat tatatatata tataatatat atatatatat
    82681 aataaaaaaa aatatataat ataatatata tatataaaat aataaattat atatatatat
    82741 ataaaataat aaaaaataat aatcatatga attttataaa tataattatt attaataata
    82801 ataataataa taataaagtc cggtccgccc cgcggagggg gcggaccccc gaaggagtgc
    82861 gggaccccgt gggaaccgca tcccttttta ttcttaatta agaaggagat aataatttat
    82921 aaaaattaat atttatttta tgtaatatta atattaatat taatataata taatataata
    82981 taatacggat taaatattac cagttgttca caggtaatat aaaatcctat tgtttcacct
    83041 attattaatt aatagttccg gggcccggcc acgggagccg gaaccccgaa aggagaataa
    83101 gtatatataa taaaatttaa taaaaaaaaa taattatata ataaatatat atattataat
    83161 attatataaa tataaaatat aattgatatt aacattatat aattaataat ataatcaaat
    83221 aatataaata taatataaaa agttttaatt attaaaatta tataaatatt atttaataaa
    83281 aataaaaata ataataataa taataataat aaagtccggt ccgccccctc cgcggagggg
    83341 gcggaccccg aaagagtgag ggaccccccc gtatacttac ggggggagaa ccgaacccct
    83401 ttttttattt aaagaaggag ataaatattt atatctttat ttataattat atataaataa
    83461 aagtttatta aaatttataa taataatata aaaaagtata taataaattt attataaata
    83521 aataaatatt tagtaataat atttaataaa attataaata ttataaataa aatattaata
    83581 ataaataata aatatataat ataatataat ataataaatt aataacaata agatatccgg
    83641 gtcccctaaa taattattat ataaaataat aattgggacc catacatata aatataaaat
    83701 attttaatat ttatatataa ataataataa tatatattta tattatatta taatataacc
    83761 ctttccaatt aatattaata ttaatattaa ttacttcctt aaaaaaataa taattaatta
    83821 attgattttt atattaatat aaaaaagtta atatatatat ttatatataa ataatataaa
    83881 ttaatataaa gataataagt ccccgctttc agcgcagtga gggaccccct cccgtaaata
    83941 tacgggaggg gagaccgaac cccaaaggaa taataaataa tagtatgtat ttaaataaat
    84001 atttaatata ctattttttt ttattatttt tataatatat ttataataat atatttaatt
    84061 ataatttata aaaaagagat ataatatttt attatatata atattaatat aatacaaatt
    84121 aacattattt aattattatt aataatattt aactttatta ttatcttcta cggttggact
    84181 ccttcttaaa aaggggttcg gtccccctcc cattagggag gggtccctca ctccttcggg
    84241 gtccgcgccc cccgcggggg ggggcggacc ggactattat tactatttat ttattaataa
    84301 taaataataa attataaagt cactgaaaga gtgaggaatt ttccttttcc caagggaaaa
    84361 ccccaaagga taatataaat attataaaat ttttattaaa taatataaaa ttcaataaaa
    84421 taattttaat taattaatta attaattaat ataaaaataa atatttttaa ttaatattaa
    84481 tattaatagt tccggggccc ggccacggga gccggaaccc cggaaggaga aatataaata
    84541 taatagtata gtatatagga agttaataat aatataaata ttatataata tatatatgta
    84601 tatatattat attatataat taattttctc cttttgtatt tacatcttaa taaaatataa
    84661 aatataaaat gttattaaca ataaaaatta ttaatcttta taatattaat aatagtaaat
    84721 ttatttatat atctccttta ggatggactc cttcggccgg actccttcgg ggtccgcccc
    84781 gcgggggcgg gccggactat ttttattttt tttttaaaaa atattaaata ttataaatat
    84841 attataaata tattataaat atgttataaa tatattataa atagaatata atataatatt
    84901 atatattata atgataaaga ttatatatat tttctttttt tttttattta ttatttttaa
    84961 taagtaaaaa ttatattata tatatatata tattagattt tataagtaat ataatataag
    85021 tattaatata taaatgcaat atgatgtaat tggttaacat tttagggtca tgacctaatt
    85081 atatacgttc aaatcgtatt attgctaata aattaatata taatatttat aaaaaagtat
    85141 aataaaatat attataagaa gaatatatta tataataatt atattaataa tattaataaa
    85201 taatatataa ataattataa aaaagtatat aatattaatc aattaattaa ttaataaata
    85261 taaataatat attaattttt aattaatttg aataagatat ttatattatt aataggaaag
    85321 tcataaatat ataaattata ttatataatt aatataataa taaaataaat tatatatttt
    85381 atttataata ttatttcttt ataagataaa atattatctg atgaataatt agattgaata
    85441 atatttataa agaaatatat ataaaaagtc attatataaa tttaattata atttaaataa
    85501 attttatata aattaatata atattaataa agtaattagt ataaataaat aatatgaaaa
    85561 taaaacttaa taaatatata aatatagtcc ggcccgcccc cccgcggcgg gcggaccccg
    85621 aaggagtgag ggacccctcc ctaatgggag ggggaccgaa ccccttttta agaaggagtc
    85681 catatatata tattaataaa aaaaagtaat atatatatat atattggaat agttatatta
    85741 ttatacagaa atatgcttaa ttataatata atatccata  
//`