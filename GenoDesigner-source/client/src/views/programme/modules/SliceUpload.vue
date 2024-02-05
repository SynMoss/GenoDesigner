<template>
  <div>
    <div class="slice-upload" id="slice-upload"></div>
    <progress-dialog
      ref="progressDialog"
      :percentage="percentage"
      @cancel="cancel"
    ></progress-dialog>
  </div>
</template>
<script>
let uploader;
import { zh_list } from "@/data/constant";
import ProgressDialog from "./ProgressDialog";
import { getToken } from "@/utils/auth";
import { mergeBigFiles } from "@/api/upload";
import {
  cutSequence,
  importConfig,
  cancelCutSequence,
  importFeature,
  importFeatureFromGb
  ,listBySequenceId
} from "@/api/system/sequence";
export default {
  components: { ProgressDialog },
  props: {
    programmeId: {
      type: String,
      default: function (params) {
        return null;
      },
    },
    sequenceName: {
      type: String,
      default: function(params) {
        return null
      }
    },
    openImportFeatureFromGbCheckAll:{
      type: Boolean,
      default: function(params) {
        return false
      }
    },
    isEditFeatureName:{
      type: false,
      default: function(){
        return false
      }
    },
    openImportFeatureCheckAll:{
      type: Boolean,
      default: function(params) {
        return false
      }
    }
  },
  data() {
    return {
      zh_list,
      uploadOptions: {
        target: process.env.VUE_APP_BASE_API + "/system/uploadBigFile",
        singleFile: false,
        chunkSize: 5 * 1024 * 1024,
        headers: { Authorization: getToken() },
        testChunks: false,
      },
      accepts: [
        ".fa.gz,.fna.gz,.fasta.gz,.gb.gz,.gbk.gz,.gbff.gz,.gtf.gz,.gff.gz,.gff3.gz,.fa,.fna,.fasta,.gb,.gbk,.gbff,.gtf,.gff,.gff3,.txt",
        ".gb,.gbk,.gbff,.gb.gz,.gbk.gz,.gbff.gz,.txt",
      ],
      gbArray: [".gb.gz", ".gbk.gz", ".gbff.gz", ".gb", ".gbk", ".gbff"],
      faArray: [".fa.gz", ".fna.gz", ".fasta.gz", ".fa", ".fna", ".fasta"],
      gffArray: [".gtf.gz", ".gff.gz", ".gff3.gz", ".gtf", ".gff", ".gff3"],
      uploadLoading: false,
            type: 0,
      percentage: 0,
      timer: null,
            chooseFile: null,
      sequenceId: null,
    };
  },
  mounted() {
    let timerA = setInterval(() => {
      if(Uploader){
      clearInterval(timerA)
      uploader = new Uploader(this.uploadOptions);
      uploader.assignBrowse(
        document.getElementById("open-local"),
        undefined,
        undefined,
        { accept: this.accepts[0] }
      );
      uploader.assignBrowse(
        document.getElementById("open-config"),
        undefined,
        undefined,
        { accept: this.accepts[1] }
      );
      uploader.assignBrowse(
        document.getElementById("open-feature"),
        undefined,
        undefined,
        { accept: this.gffArray }
      );
      uploader.assignBrowse(
        document.getElementById("open-gb"),
        undefined,
        undefined,
        { accept: this.gbArray }
      );
      uploader.assignBrowse(
        document.getElementById("open-config-single"),
        undefined,
        undefined,
        { accept: '.txt' }
      );
      uploader.on("filesSubmitted", (files, fileList, event) => {
        this.$emit("begin");
        uploader.upload();
      });
      uploader.on("fileProgress", () => {
        let percentage = Math.trunc(uploader.progress() * 50);
        if (percentage > this.percentage) {
          this.percentage = percentage;
        }
      });
      uploader.on("filesAdded", (files) => {
        if (this.type === 1) {
          if (files.length !== 2) {
            this.$message.warning("Only allow uploading two file");
            return false;
          }
          if (files[0].name.endsWith("txt") && files[1].name.endsWith("txt")) {
            this.$message.warning("Missing gb file");
            return false;
          }
          if (
            !files[0].name.endsWith("txt") &&
            !files[1].name.endsWith("txt")
          ) {
            this.$message.warning("Missing txt file");
            return false;
          }
        }
        if (this.type === 4) {
          if (files.length !== 1) {
            this.$message.warning("Only allow uploading one file");
            return false;
          }
          if (!files[0].name.endsWith("txt")) {
            this.$message.warning("Missing txt file");
            return false;
          }
        }
        if (this.type === 0) {
          for (let i = 0; i < files.length; i++) {
            let name = files[i].name;
            let isAccept = false;
            let accepts = this.accepts[0].split(",");
            for (let j = 0; j < accepts.length; j++) {
              let a = accepts[j];
              if (name.endsWith(a)) {
                isAccept = true;
                break;
              }
            }
            if (!isAccept) {
              this.$message.warning("The file does not meet the requirements");
              return false;
            }
          }
          let isGB = false,isTxt=false;
          for (let i = 0; i < files.length; i++) {
            let name = files[i].name;
            if (this.gbArray.findIndex((g) => name.endsWith(g)) > -1) {
              isGB = true;
              break;
            }
          }
                    if (!isGB) {
            if(files.length === 2){
              if (
                this.faArray.findIndex((g) => files[0].name.endsWith(g)) === -1 &&
                this.faArray.findIndex((g) => files[1].name.endsWith(g)) === -1
              ) {
                this.$message.warning("The file does not meet the requirements");
                return false;
              }
              if (
                this.gffArray.findIndex((g) => files[0].name.endsWith(g)) ===
                  -1 &&
                this.gffArray.findIndex((g) => files[1].name.endsWith(g)) === -1
              ) {
                this.$message.warning("The file does not meet the requirements");
                return false;
              }
            }else if(files.length === 1){
              if (
                this.faArray.findIndex((g) => files[0].name.endsWith(g)) === -1
              ) {
                this.$message.warning("The file does not meet the requirements");
                return false;
              }
            }else{
              this.$message.warning("The file does not meet the requirements");
              return false;
            }
          } else {
                        if(files.length>2){
              for (let i = 0; i < files.length; i++) {
                let name = files[i].name;
                if (this.gbArray.findIndex((g) => name.endsWith(g)) === -1) {
                  this.$message.warning("The file does not meet the requirements");
                  return false;
                }
              }
            }else if(files.length===2){
                            if ((this.gbArray.findIndex((g) => files[0].name.endsWith(g)) === -1
                || this.gbArray.findIndex((g) => files[1].name.endsWith(g)) === -1)&&
                (!files[0].name.endsWith("txt") &&
            !files[1].name.endsWith("txt"))
              ) {
                this.$message.warning("The file does not meet the requirements");
                return false;
              }
            }
          }
          if(files.length===1){
            this.chooseFile = 0
          }else{
            this.chooseFile = 1
            for (let i = 0; i < files.length; i++) {
              let name = files[i].name;
              if (this.gbArray.findIndex((g) => name.endsWith(g)) === -1) {
                this.chooseFile = 0
                break;
              }
            }
          }

        }
        if (this.type === 2) {
          for (let i = 0; i < files.length; i++) {
            let name = files[i].name;
            let isAccept = false;
            for (let j = 0; j < this.gffArray.length; j++) {
              let a = this.gffArray[j];
              if (name.endsWith(a)) {
                isAccept = true;
                break;
              }
            }
            if (!isAccept) {
              this.$message.warning("The file does not meet the requirements");
              return false;
            }
          }
        }
        if (this.type === 3) {
          for (let i = 0; i < files.length; i++) {
            let name = files[i].name;
            let isAccept = false;
            for (let j = 0; j < this.gbArray.length; j++) {
              let a = this.gbArray[j];
              if (name.endsWith(a)) {
                isAccept = true;
                break;
              }
            }
            if (!isAccept) {
              this.$message.warning("The file does not meet the requirements");
              return false;
            }
          }
        }
        clearInterval(this.timer);
        this.$refs.progressDialog.show();
        return true;
      });

      let mergeParams = [];
      uploader.on("fileComplete", (rootFile) => {
        mergeParams.push({
          identifier: rootFile.uniqueIdentifier,
          filename: rootFile.name,
          totalSize: rootFile.size,
          folderPath: this.programmeId,
        });
      });
      uploader.on("fileError", (rootFile, file, message) => {
        console.log(rootFile, file, message);
      });
      uploader.on("complete", () => {
        this.mergeFiles(mergeParams);
        uploader.cancel();
        mergeParams = [];
      });
      }
    },500)
  },
  methods: {
    cancel() {
      if (this.timer) {
        clearInterval(this.timer);
      }
      this.percentage = 0;
      uploader.cancel();
      cancelCutSequence({ programmeId: this.programmeId });
      this.$message.error("Canceled");
      this.$emit("cancel");
                                  },
    mergeFiles(mergeParams) {
      this.timer = setInterval(() => {
        if (this.percentage <= 94) {
          this.percentage = this.percentage + Math.ceil(Math.random() * 5);
        } else {
          clearInterval(this.timer);
        }
      }, 1000);
      mergeBigFiles(mergeParams).then(async (res) => {
        let sequenceList
        if (this.type === 2) {
          if(this.openImportFeatureCheckAll){
            await listBySequenceId({sequenceId: this.sequenceId}).then(res=>{
              sequenceList = res.data
            })
          }else{
            sequenceList = [{id:this.sequenceId,name:this.sequenceName}]
          }
          for (let index = 0; index < sequenceList.length; index++) {
            await importFeature(res.data, { sequenceId: sequenceList[index].id})
            .then((res) => {
              this.$notify({
                title: 'success',
                message: sequenceList[index].name+' done ',
                type: 'success'
              });
            }).catch(err=>{
              this.$notify.error({
                title: sequenceList[index].name+ ' fail',
                message: err.message,
                duration: 0
              });
            }).finally(err=>{
              if(index===sequenceList.length-1){
                this.percentage = 100;
                this.$refs.progressDialog.close();
                this.$emit("ok");
                clearInterval(this.timer);
                setTimeout(() => {
                  this.percentage = 0;
                }, 50);
              }
            })
          }
        } else if (this.type === 3) {
          if(this.openImportFeatureFromGbCheckAll){
            await listBySequenceId({sequenceId: this.sequenceId}).then(res=>{
              sequenceList = res.data
            })
          }else{
            sequenceList = [{id:this.sequenceId,name:this.sequenceName}]
          }
          for (let index = 0; index < sequenceList.length; index++) {
            await importFeatureFromGb(res.data, { sequenceId: sequenceList[index].id})
            .then((res) => {
              this.$notify({
                title: 'success',
                message: sequenceList[index].name+' done ',
                type: 'success'
              });
            }).catch(err=>{
              this.$notify.error({
                title: sequenceList[index].name+ ' fail',
                message: err.message,
                duration: 0
              });
            }).finally(err=>{
              if(index===sequenceList.length-1){
                this.percentage = 100;
                this.$refs.progressDialog.close();
                this.$emit("ok");
                clearInterval(this.timer);
                setTimeout(() => {
                  this.percentage = 0;
                }, 50);
              }
            })
          }
        }  else if (this.type === 4) {
          importConfig(res.data, { sequenceId: this.sequenceId,isEditFeatureName:this.isEditFeatureName?1:0 }).then(
            (res) => {
              this.percentage = 100;
              this.$refs.progressDialog.close();
              this.$emit("ok");
              clearInterval(this.timer);
              setTimeout(() => {
                this.percentage = 0;
              }, 50);
            }
          );
        } else {
          cutSequence(res.data, {
            programmeId: this.programmeId,
            chooseFile: this.chooseFile,
          }).then((res) => {
            this.percentage = 100;
            this.$refs.progressDialog.close();
            this.$emit("ok", res.data, res.chooseFile);
            clearInterval(this.timer);
            setTimeout(() => {
              this.percentage = 0;
            }, 50);
          });
        }
      });
    },
    changeUploadType(type,sequenceId) {
      this.type = type;
      this.sequenceId = sequenceId
    },
  },
};
</script>
<style lang="scss">
.slice-upload {
  display: none;
}
</style>
