<template>
  <el-dialog
      :close-on-click-modal="false"
      title="Preview"
      :visible.sync="visible"
      width="1000px"
      append-to-body
    >
    <div ref="ove" :style="{ height: height }"></div>
  </el-dialog>
</template>
<script>
import { getAction } from '@/api/manage'
import {
  genbankToJson,
  jsonToGenbank,
  fastaToJson,
  anyToJson
} from 'bio-parsers'
import { genId } from '@/utils/hmzhkj'
let oveEditor
export default {
  props: {
  },
  data() {
    return {
      loading: false,
      width: '1000px',
      height: '500px',
      model: {sequenceData:{}},
      visible: false,
      MOCK_FASTA:'',
    }
  },
  computed: {},
  created() {},
  mounted() {},

  methods: {
    createOve() {
      this.loading = true
      setTimeout(() => {
        const oveDom = this.$refs.ove
        oveEditor = window.createVectorEditor(oveDom, {
          withPreviewMode: false,
          editorName: 'FirstSequence',
          showMenuBar: true,
          ToolBarProps: {
            toolList: [
              'undoTool',
              'redoTool',
              'cutsiteTool',
              'featureTool',
              'alignmentTool',
                            'orfTool',
                            'editTool',
              'findTool',
              'visibilityTool'
                          ]
          },
          disableSetReadOnly: true,
          showReadOnly: false,
          onCopy: function(event, copiedSequenceData, editorState) {
            console.log('Click to copy')
          }
        })

        this.udpateOve()
      }, 50)
    },
    async udpateOve() {
        let res = await anyToJson(this.MOCK_FASTA)
        console.log(res)
        let sequenceData = res[0].parsedSequence
      oveEditor.updateEditor({
        sequenceData: sequenceData,
        panelsShown: [
          [
            {
              id: 'circular',
              name: 'Circular Map',
              active: sequenceData.circular
            },
            {
              id: 'rail',
              name: 'Linear Map',
              active: !sequenceData.circular
            }
          ],
          [
            {
              id: 'sequence',
              name: 'Sequence Map',
              active: true
            },
            {
              id: 'properties',
              name: 'Properties',
              active: false
            }
          ]
        ]
      })
      this.loading = false
          },
    async show(row) {
      const seq = `>Untitled Sequence||${row.length}|circular
      ${row.content}`

      this.MOCK_FASTA = seq
      this.createOve()
      this.visible = true
    },
  }
}
</script>
<style lang="scss" scoped>
@import "@/assets/styles/variables.scss";
.left {
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  padding: 0 10px;
}
.left-choose {
  background-color: rgb(235, 241, 255);
  color: $base-color;
}
.ove-dialog-header {
  background-color: #ebf1f5;
  height: 100%;
  width: 100%;
  padding-top: 18px;
  padding-left: 20px;
}
</style>
