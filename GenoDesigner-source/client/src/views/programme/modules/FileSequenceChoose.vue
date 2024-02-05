<template>
  <div ref="oveA" class="ove-choose">
  </div>
</template>
<script>
import { listSequenceFile } from "@/api/system/sequence";
import { genId } from "@/utils/hmzhkj";
export default {
  components: {
  },
  props: {
    programmeId:{
      type: String,
      default: function(params) {
        return null
      }
    },
  },
  data() {
    return {
      oveChoose: null
    }
  },
  created() {},
  mounted(){
  },
  computed: {
    alignmentTracks(){
        let array = []
        this.sequenceList.forEach(item=>{
            array.findIndex()
        })
    }
  },
  methods: {
    show({name,start,currentSeqIndex}){
      listSequenceFile({name:name,programmeId:this.programmeId,start:start}).then(res=>{
        const alignmentTracks = []
        res.data.forEach(item => {
          item.features || (item.features = [])
                    item.features.forEach(feat=>{
            if(!feat.locations){
              feat.locations = [{start:feat.start,end:feat.end}]
            }
            feat.start = feat.start - currentSeqIndex
            feat.end = feat.end - currentSeqIndex
            feat.locations.forEach(location=>{
              location.start = location.start - currentSeqIndex
              location.end = location.end - currentSeqIndex
            })
          })
          item.name = item.source
          alignmentTracks.push({alignmentData:item,sequenceData:item})
        });
        console.log(alignmentTracks)
        this.oveChoose = window.createAlignmentView(this.$refs.oveA,{
          alignmentTracks:alignmentTracks,
          id: genId(),
          handleAlignmentRename: () => {
            console.info("alignment being renamed!");
          },           alignmentAnnotationVisibility: {
            features: true,
            parts: false
          },
          linearViewOptions: () => {
            return null
          },
          selectionLayerRightClicked: (item)=>{
              console.log(item)
            }
          })
        this.$emit('ok')
      })
    },
  },
  watch:{
  }
}
</script>
<style lang="scss">
.ve-alignment-top-bar{
  .bp3-input-group.bp3-small{
    display: none;
  }
  .bp3-button.bp3-small{
    display: none;
  }
}
</style>
