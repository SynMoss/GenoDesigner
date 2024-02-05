<template>
  <div class="insert-dialog">
    <el-dialog
      :close-on-click-modal="false"
      :title="language==='zh'?zh_list.insertA:'Insert by Feature Type'"
      :visible.sync="visible"
      width="1400px"
      append-to-body
    >
      <el-form label-position="top" style="height:610px;overflow:auto;">
        <div v-if="showType">
          <el-form-item >
            <el-checkbox v-model="checkAll">{{language==='zh'?zh_list.applyToAllChromosomes:'apply to all chromosomes'}}</el-checkbox>
          </el-form-item>
          <el-form-item :label="language==='zh'?zh_list.nameA:'Name'">
            <el-input
              v-model="featureName"
              size="mini"
              clear
              class="programmer-search-input"
              style="width: 200px"
            >
            </el-input>
          </el-form-item>
          <el-form-item :label="language==='zh'?zh_list.typeA:'Type'">
            <el-input
              v-model="featureType"
              size="mini"
              clear
              class="programmer-search-input"
              style="width: 200px"
            >
            </el-input>
          </el-form-item>
          <el-form-item :label="language==='zh'?zh_list.chooseA:'Choose'">
            <el-checkbox-group v-loading="getLoading" v-model="typeRemainList">
              <el-row>
                <el-col :span="24" v-for="(item,index) in group" :key="item.key">
                  <el-checkbox :label="item" :key="item">{{item}}</el-checkbox>
                  <el-row v-if="typeRemainList.indexOf(item)!==-1">
                    <el-col :span="4">
                      <el-form-item >
                        <div slot="label"><el-switch v-model="group0[index]" :active-text="language==='zh'?zh_list.aao:'Insert upstream'"></el-switch></div>
                        <el-input-number v-show="group0[index]"
                          v-model="groupMap[item][0]"
                          :min="0"
                          :max="99999999"
                          :precision="0"
                          :controls="false" size="mini"
                        >
                        </el-input-number>
                      </el-form-item>
                    </el-col>
                    <el-col :span="4">
                      <el-form-item>
                        <div slot="label"><el-switch v-model="group1[index]" :active-text="language==='zh'?zh_list.aap:'Insert downstream'"></el-switch></div>
                        <el-input-number  v-show="group1[index]"
                          v-model="groupMap[item][1]"
                          :min="0"
                          :max="99999999"
                          :precision="0"
                          :controls="false"
                          size="mini"
                        >
                        </el-input-number>
                      </el-form-item>
                    </el-col>
                    <el-col :span="6">
                      <el-form-item :label="language==='zh'?zh_list.aaq:'Adjust sequence orientation based on feature direction'" :show-overflow-tooltip="true">
                        <el-radio-group v-model="groupCheck[index]">
                          <el-radio :label="true">{{language==='zh'?'Yes':'Yes'}}</el-radio>
                          <el-radio :label="false">{{language==='zh'?'No':'No'}}</el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-col>
                    <el-col :span="5" v-show="groupCheck[index]">
                      <el-form-item :label="language==='zh'?zh_list.aar:'Upstream relative direction'" :show-overflow-tooltip="true">
                        <el-radio-group v-model="group3[index]">
                          <el-radio :label="1">{{language==='zh'?zh_list.aas:'Same direction'}}</el-radio>
                          <el-radio :label="-1">{{language==='zh'?zh_list.aat:'Reverse direction'}}</el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-col>
                    <el-col :span="5" v-show="groupCheck[index]">
                      <el-form-item :label="language==='zh'?zh_list.aau:'Downstream relative direction'" :show-overflow-tooltip="true">
                        <el-radio-group v-model="group4[index]">
                          <el-radio :label="1">{{language==='zh'?zh_list.aas:'Same direction'}}</el-radio>
                          <el-radio :label="-1">{{language==='zh'?zh_list.aat:'Reverse direction'}}</el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item :label="language==='zh'?zh_list.aav:'Feature to protect'">
            <el-checkbox-group v-loading="getLoading" v-model="typeList">
              <el-col :span="24" v-for="(item) in group" :key="item.key">
                  <el-checkbox :label="item" :key="item">{{item}}</el-checkbox>
              </el-col>
            </el-checkbox-group>
          </el-form-item>
          <div style="margin: 15px 0;"></div>
        </div>
        <el-checkbox v-model="strand">Reverse complement</el-checkbox>
        <div style="margin: 15px 0;"></div>
        <el-tabs v-model="activeName">
          <el-tab-pane :label="language==='zh'?zh_list.aaw:'From Parts Library'" name="a">
            <div class="part-list-top">
                  <div>&nbsp;</div>
                  <div class="part-list-top-right">
                    <el-radio-group v-model="queryType" class="query-radio">
                      <el-radio label="name" che>{{language==='zh'?zh_list.aax:'Fuzzy Search (by Name)'}}</el-radio>
                      <el-radio label="code">{{language==='zh'?zh_list.aay:'Precision Search (by ID)'}}</el-radio>
                    </el-radio-group>
                    <span style="width: 50px"></span>
                    <el-input
                      size="small"
                      :placeholder="placeholderA"
                      @keyup.enter.native="reset"
                      v-model="input"
                      @blur="input = $event.target.value.trim()"
                    >
                    </el-input>
                    <span style="width: 30px"></span>
                    <el-button type="primary" @click="reset">{{language==='zh'?zh_list.searchA:'Search'}}</el-button>
                    <el-button type="primary" @click="resetDefult"
                      >{{language==='zh'?zh_list.resetA:'Resetting'}}</el-button
                    >
                  </div>
                </div>

            <vxe-table
              ref="xTable"
              @radio-change="selectChangeEvent"
              :radio-config="{ highlight: true ,visibleMethod:({row})=>row.length}"
              :sort-config="{ remote: true }"
              @sort-change="sortChangeEvent"
              :filter-config="{ remote: true }"
              @filter-change="filterChangeEvent"
              border="inner"
              class="table-self"
              header-cell-class-name="table-header-self"
              cell-class-name="table-cell-self"
              :align="allAlign"
              :data="tableData"
              :loading="tableLoading"
              :seq-config="{
                startIndex:
                  (this.tablePage.currentPage - 1) * this.tablePage.pageSize,
              }"
            >
              <vxe-column type="radio" width="80" :title="language==='zh'?zh_list.chooseA:'Select'"></vxe-column>
              <vxe-column
                field="code"
                :title="language==='zh'?zh_list.aaz:'Biological Part ID'"
                :filters="typeFilters"
              ></vxe-column>
              <vxe-column
                field="name"
                :title="language==='zh'?zh_list.nameA:'Name'"
                :filters="typeFilters"

              ></vxe-column>
              <vxe-column
                field="type"
                :title="language==='zh'?zh_list.typeA:'Type'"
                :filter-config="{ remote: true }"
              ></vxe-column>
                             <vxe-column field="length" :title="language==='zh'?zh_list.aAa:'Size'"></vxe-column>
              <vxe-column field="description" :title="language==='zh'?zh_list.aAb:'Description'"></vxe-column>
            </vxe-table>
            <div class="pager-flex">
              <div class="pager-flex-total">
                {{language==='zh'?''+ tablePage.totalResult +' ':'Data Found by All '+tablePage.totalResult +' Terms!'}}
              </div>
              <vxe-pager
                class-name="pager-self"
                size="small"
                border
                :loading="tableLoading"
                :current-page="tablePage.currentPage"
                :page-size="tablePage.pageSize"
                :total="tablePage.totalResult"
                :layouts="[
                  'PrevPage',
                  'JumpNumber',
                  'NextPage',
                  'Sizes',
                  'FullJump',
                ]"
                @page-change="pageChangeEvent"
              ></vxe-pager>
            </div>
          </el-tab-pane>
          <el-tab-pane :label="language==='zh'?zh_list.aAc:'Customize'" name="b">
            <el-button
              @click="toUpload"
              style="margin-bottom: 10px;"
              :disabled="uploadLoading"
              :loading="uploadLoading"
            >
              {{language==='zh'?zh_list.aAd:'Open Local File'}}
            </el-button>
            <input
              @click.stop
              class="insert-upload"
              ref="uploadPart"
              id="uploadPart"
              type="file"
              accept=".fa,.fasta"
              @change="uploadPart"
            />
            <el-input
              type="textarea"
              :clearable="true"
              v-model="content"
              rows="20"
              resize="none"
            />
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <div slot="footer">
        <div>
          <el-button @click="cancel">{{language==='zh'?zh_list.cancelA:'Cancel'}}</el-button>
          <el-button
            type="primary"
            @click="submitForm"
            :disabled="subLoading"
            :loading="subLoading"
          >
            {{language==='zh'?zh_list.confirmA:'OK'}}
          </el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { zh_list } from "@/data/constant";
import { ONLY_Char_ARRAY } from '@/data/constant'
import { listPart } from "@/api/system/part";
import { anyToJson } from 'bio-parsers'
import { listType } from '@/api/system/sequence'
export default {
  name: 'InsertDialog',
  props: {
    sequenceId: {
      type: Number,
      default: function (params) {
        return null;
      },
    },
    programmeId: {
      type: String,
      default: function (params) {
        return null;
      },
    },
  },
  data() {
    return {
      zh_list,
      strand: false,
      checkAll: false,
      language: process.env.VUE_APP_LANGUAGE,
      activeName: 'a',
      disabled: true,
      allAlign: null,
      subLoading: false,
      getLoading: false,
      visible: false,
      model: {},
      content: null,
      modelDefault: {},
      tableLoading: false,
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 0,
      },
      tableData: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      typeFilters: [
        {
          label: 'MutationSingle/Combinatorial',
          value: 'Filter',
        },
      ],
      input: '',
      uploadLoading: false,
      queryType: 'name',
      hasBindFlag: false,
      showType: false,
      checkedRow:{},
      group: [],
      groupCheck: [],
      group0: [],
      group1: [],
      group3: [],
      group4: [],
      groupMap:{},
      typeRemainList: [],
      typeList: [],
      featureType: null,
      featureName: null
    }
  },
  computed: {
    placeholderA(){
      if(this.language==='zh'){
        return this.queryType === 'name'?this.zh_list.aAe:this.zh_list.aAf
      }else{
        return this.queryType === 'name'?'Please Type in Name':'Please Type in IDs'
      }
    }
  },
  created() {
    this.loadData()
  },
  methods: {
        reset() {
      this.tablePage.currentPage = 1
      this.tablePage.pageSize = 10
      this.loadData()
    },
    loadData() {
      this.queryComponent()
    },
    selectChangeEvent({ checked,row }) {
      this.checkedRow = row;
      if (this.checkedRow.length > 0 && this.activeName == 'a') {
        this.disabled = false;
      } else {
        this.disabled = true;
      }
    },
    sortChangeEvent({ property, order }) {
      console.log(property)
      console.log(order)
    },
    filterChangeEvent({ property, values }) {
      console.log(property)
      console.log(values)
    },
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.queryComponent()
    },
    toUpload() {
      document.getElementById('uploadPart').click()
    },
        uploadPart(e) {
      let file = e.target.files[0]
      if (file) {
        this.uploadLoading = true
        anyToJson(file)
          .then((res) => {
            if (res.length > 1) {
              this.$message.error('Contains Multiple Sequence Files')
            } else {
              this.content = res[0].parsedSequence.sequence
              this.submitForm()
            }
          })
          .catch((err) => {
            this.$message.error('Error Parsing File')
          })
          .finally(() => {
            this.uploadLoading = false
            document.getElementById("uploadPart").value = ""
          })
      }
    },
    show(showType) {
      this.checkAll = false
      this.strand = false
      this.content = null
      this.visible = true
      this.showType = showType
      this.typeRemainList = []
      this.typeList = []
      this.groupMap = {}
      this.groupCheck = []
      this.group0 = []
      this.group1 = []
      this.group3 = []
      this.group4 = []
      this.featureName = null
      this.featureType = null
      if(showType){
        listType({programmeId: this.programmeId}).then(res=>{
          this.group = res.data
          this.group.forEach(item=>{
            this.groupMap[item]=[0,0,true,0,0]
            this.groupCheck.push(false)
            this.group0.push(false)
            this.group1.push(false)
            this.group3.push(1)
            this.group4.push(1)
        })
          this.getLoading = false
        })
      }
    },
    cancel() {
      this.visible = false
    },
    async submitForm() {
      let str = ''
      if (this.activeName == 'a') {
        str = this.checkedRow.content
      } else {
        str = this.content
        if (!str) {
          this.$message.warning('Please Input')
          return
        }
        for (let j = 0; j < str.length; j++) {
          if (ONLY_Char_ARRAY.indexOf(str[j]) === -1) {
            this.$message.warning('Can Only Input '+ONLY_Char_ARRAY.toString())
            return
          }
        }
      }
      let subData = { content: str, isRollback: false,showType: this.showType,featureTypeList: this.typeRemainList }
      if(this.showType){
        if(!this.typeRemainList.length){
          this.$message.warning('Please Choose Type')
          return
        }
        subData.typeList = this.typeList
        let typeRemainMap = {}
        this.typeRemainList.forEach(item=>{
          let index = this.group.indexOf(item)
          typeRemainMap[item] = this.groupMap[item]
          typeRemainMap[item][2] = this.groupCheck[index]
          typeRemainMap[item][3] = this.group3[index]
          typeRemainMap[item][4] = this.group4[index]
          if(!this.group0[index]){
            typeRemainMap[item][0] = '-'
          }
          if(!this.group1[index]){
            typeRemainMap[item][1] = '-'
          }
        })
        subData.featureMap = typeRemainMap
        subData.featureType = this.featureType
        subData.featureName = this.featureName
        subData.sequence = str
        subData.checkAll = this.checkAll
        if(!this.featureType){
          this.$message.warning('Please enter the type')
          return
        }
        if(!this.featureName){
          this.$message.warning('Please enter the name')
          return
        }
      }
      subData.strand = this.strand
      this.$emit('ok', subData)
      this.visible = false
    },
    queryComponent() {
      this.queryParams.pageNum = this.tablePage.currentPage
      this.queryParams.pageSize = this.tablePage.pageSize
      this.tableLoading = true
      this.queryParams.remo
      this.queryParams[this.queryType] = this.input
      console.log(this.queryParams)
      listPart(this.queryParams)
        .then((res) => {
          this.tablePage.totalResult = res.total;
          this.tableData = res.rows;
          console.log(res)
        })
        .finally(() => {
          this.tableLoading = false;
        });
    },
    resetDefult() {
      this.input = ''
      this.queryParams = {}
      this.reset()
    },

  },
}
</script>
<style lang="scss" scoped>
.insert-upload {
  display: none;
}
.insert-dialog-footer {
  display: flex;
  justify-content: space-between;
}
.part-list-top {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  .part-list-top-right {
    flex: 0 1 940px;
    display: flex;
    justify-content: space-between;
    > * {
      display: flex;
      align-items: center;
    }
  }
}
</style>
