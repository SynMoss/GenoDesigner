<template>
  <div class="range-search" @click="rangeClick">
    <div class="slider-number">
      <div :style="{ left: sliderLeft + 10 + 'px' }">{{ currentStart }}</div>
    </div>
    <div class="range-f" @click="clickRange">
      <div class="range" ref="range">
        <div
          ref="slider"
          class="slider"
          :style="{ width: sliderWidth + 'px', left: sliderLeft + 'px' }"
        >|&nbsp;&nbsp;|&nbsp;&nbsp;|</div>
      </div>
      <div class="range-f-number">
        <span>1</span>
        <span>{{ bpLength }}</span>
      </div>
    </div>
    <div class="search-f">
      <div class="search">
        <div class="search-left">
          <el-tooltip v-if="!showChooseFile" class="item" effect="dark"  placement="top">
             <div slot="content">
                <div v-for="item in sourceFileNameShow.split(',')" :key="item">{{item}}</div>
            </div>
            <span>{{ sourceFileNameShow.length>20?sourceFileNameShow.substring(0,15)+'...':sourceFileNameShow}}</span>
          </el-tooltip>
        </div>
        <div class="center">
                     <vxe-pulldown ref="xDown2">
            <template #default>
              <vxe-input class="select-input" v-model="sequenceSearchValue" placeholder="" @focus="focusEvent" @keyup="fkeyupEvent"></vxe-input>
            </template>
            <template #dropdown>
              <vxe-list height="200" class="choose" :data="sequenceListShow" >
                <template #default="{ items }">
                  <div v-for="item in items" :class="item.id===sequenceId?'left left-choose':'left'" :key="item.id" @click="changeSequence(item)">
                    <span>{{ item.name }}</span>
                  </div>
                </template>
              </vxe-list>
            </template>
          </vxe-pulldown>
          <div class="center-children-2">:</div>
          <el-input
            v-model="value2"
            size="mini"
            :placeholder="language==='zh'?zh_list.aAx:'Format 1..123'"
            clear
            class="programmer-search-input"
            style="width: 200px"
          >
          </el-input>
          <el-button
            @click="search"
            class="programmer-search-btn"
            type="primary"
            size="mini"
            icon="el-icon-search"
            :disabled="searchDisabled"
            :loading="searchLoading"
          ></el-button>
          <el-button type="primary"
        size="mini" @click="toSearchDialog"
            >{{language==='zh'?zh_list.aAw:'Advanced Search'}}</el-button
          >
        </div>
      </div>
    </div>
    <search-dialog
      :sequenceId="sequenceId"
      @ok="searchOk"
      ref="searchDialog"
    ></search-dialog>
  </div>
</template>
<script>
import { zh_list } from "@/data/constant";
import { debounce, ceil } from 'xe-utils'
import SearchDialog from './SearchDialog'
import { DEFAULT_BP_LENGTH } from '@/data/constant'
import { closeFeatureDialog } from "@/utils/hmzhkj";
export default {
  props: {
    sourceFileNameShow: {
      type: String,
      default: function(params) {
        return ''
      }
    },
    sequenceLengthToShow:{
      type: Number,
      default: function(params) {
        return DEFAULT_BP_LENGTH
      }
    },
    bpLength: {
      type: Number,
      default: function(params) {
        return 0
      }
    },
    sequenceList: {
      type: Array,
      default: function(params) {
        return []
      }
    },
    showChooseFile:{
      type:Boolean,
      default:function (params) {
        return false
      }
    }
  },
  components: {
    SearchDialog
  },
  data() {
    return {
      zh_list,
      language: process.env.VUE_APP_LANGUAGE,
      sequenceListShow:[],
      sequenceSearchValue: null,
      valueA: 0,
      step: 1000,
      max: 10000,
      sequenceId: null,
      value2: '',
      id: '',
      currentStart: 1,
      searchEnd: null,
            rangeWidth: 0,
            sliderWidth: 80,
      sliderLeft: 0,
            lastSliderLeft: 0,
      canMove: false,
      searchDisabled: true,
      searchLoading: false,
      mouseDownPosition: {
        x: null
      }
    }
  },
  mounted() {
    window.addEventListener('resize', (params)=> {
      this.rangeWidth = this.$refs.range.offsetWidth
      this.calSliderWidth()
    })
    setTimeout(() => {
      this.init()
    }, 50)
  },
  computed: {
    maxSliderLeft() {
      return this.rangeWidth - this.sliderWidth
    },
    percent() {
      return this.sequenceLengthToShow / this.bpLength
    },
  },
  methods: {
    rangeClick(){
      closeFeatureDialog()
    },
    init(){
      this.rangeWidth = this.$refs.range.offsetWidth
      this.calSliderWidth()
      this.onSlide()
    },
    calCurrentStart() {
      let start = ceil(
        (this.sliderLeft / this.rangeWidth) * this.bpLength,
        0
      )
      if(start<this.bpLength){
        this.currentStart = start
      }
      if(start<=0){
        this.currentStart = 1
      }
    },
    onResize: debounce(function() {

      this.rangeWidth = this.$refs.range.offsetWidth
      this.calSliderWidth()
    }, 500),
    changeSequence(item) {
      this.$refs.xDown2.hidePanel().then(()=>{
        this.sequenceListShow = this.sequenceList
      })
      this.sequenceId = item.id
      this.sequenceSearchValue = item.name
      this.sliderLeft = 0
      this.currentStart = 1
      this.rangeOk()
    },
    changeSequenceId({id,start,name}) {
      this.sequenceId = id
      this.sequenceSearchValue = name
      this.$refs.xDown2.hidePanel()
      this.sequenceListShow = this.sequenceList
      if(start){
        if(start>this.bpLength-this.sequenceLengthToShow){
          this.currentStart = this.bpLength-this.sequenceLengthToShow
        }else{
          this.currentStart = start
        }
        if(this.bpLength>this.sequenceLengthToShow){
          const percent = this.currentStart / this.bpLength
          this.sliderLeft = this.rangeWidth * percent
        }
      }else{
        this.sliderLeft = 0
        this.currentStart = 1
      }
    },
    calSliderWidth() {
      this.searchDisabled = true
      if (!this.bpLength) {
        this.sliderWidth = 40
        return
      }
      if (this.sequenceLengthToShow > this.bpLength) {
        this.sliderWidth = this.rangeWidth
        return
      }
      this.searchDisabled = false
      this.sliderWidth = this.rangeWidth * this.percent
      if (this.sliderWidth < 40) {
        this.sliderWidth = 40
      }
    },
    focusEvent () {
      this.$refs.xDown2.showPanel()
    },
    fkeyupEvent(){
      this.sequenceListShow = this.sequenceSearchValue?this.sequenceList.filter(item => item.name.indexOf(this.sequenceSearchValue) > -1) : this.sequenceList
    },
    onSlide() {
      const sliderDom = this.$refs.slider
      sliderDom.onmousedown = (e) => {
        if (this.bpLength > this.sequenceLengthToShow) {
          this.canMove = true
          this.mouseDownPosition.x = e.screenX
        }
      }
      document.onmouseup = (e) => {
        if (this.canMove) {
          this.canMove = false
          this.mouseDownPosition.x = null
          this.rangeOk()
        }
      }
      sliderDom.onmousemove = (e) => {
        if (this.canMove) {
          const sliderLeft =
            e.screenX - this.mouseDownPosition.x + this.lastSliderLeft
            if(sliderLeft<0){
              this.sliderLeft = 0
              this.currentStart = 1
            }else if(sliderLeft>this.maxSliderLeft){
              this.sliderLeft = this.maxSliderLeft
              this.currentStart = this.bpLength-this.sequenceLengthToShow
            }else{
              this.sliderLeft = sliderLeft
              this.calCurrentStart()
            }
        }
      }
    },
    clickRange(e) {
            if (e.srcElement.className !== 'range') {
        return
      }
      if(e.layerX>0 && e.layerX<this.rangeWidth){
        if (e.layerX < this.sliderLeft) {
                    this.sliderLeft = e.layerX
        } else {
                    this.sliderLeft = e.layerX - this.sliderWidth
        }
      }
      this.calCurrentStart()
      this.rangeOk()
    },
    rangeOk() {
      this.value2 = null
      this.lastSliderLeft = this.sliderLeft
      this.loadSequence({ id: this.sequenceId, start: this.currentStart })
    },
    toSearchDialog() {
      if (!this.sequenceId) {
        this.$message.warning('请选择染色体')
        return
      }
      this.$refs.searchDialog.show()
    },
    searchOk(sequence) {
      if(this.bpLength>this.sequenceLengthToShow){
        this.currentStart = sequence.start+1
        const percent = this.currentStart / this.bpLength
        this.sliderLeft = this.rangeWidth * percent
      }
      this.$emit('search-ok-high', sequence)
    },
    loadSequence: debounce(function(param,selection) {
      const queryParam = { id: param.id, start: param.start - 1 }
            this.$emit('search-ok', queryParam,selection)
    }, 500),
    jump(selection){
      const queryParam = {
        id: this.sequenceId,
        start: this.currentStart
      }
      if(this.bpLength>this.sequenceLengthToShow){
        if(selection.start>this.bpLength-this.sequenceLengthToShow){
          this.currentStart = this.bpLength-this.sequenceLengthToShow
        }else{
          this.currentStart = selection.start
        }
      }

      queryParam.start = this.currentStart
      if(this.bpLength>this.sequenceLengthToShow){
        const percent = this.currentStart / this.bpLength
        this.sliderLeft = this.rangeWidth * percent
      }
      selection.start--
      selection.end--
      this.loadSequence(queryParam,selection)
    },
    search() {
      if (!this.sequenceId) {
        this.$message.warning('请选择染色体')
        return
      }
      if (!this.value2) {
        this.$message.warning('请输入范围')
        return
      }
            if (this.sequenceLengthToShow > this.bpLength) {
        return
      }
      const selection = {}
      const queryParam = {
        id: this.sequenceId,
        start: this.currentStart
      }
      if (this.value2) {
        if (!/^\d+\.{2}\d+$/.test(this.value2)) {
          this.$message.warning(this.language==='zh'?'请输入正确的范围':'Please enter the correct range！')
          return
        }
        const arr = this.value2.split('..')
        let start = +arr[0]
        let end = +arr[1]
        if (start > end) {
          this.$message.warning(this.language==='zh'?'请输入正确的范围':'Please enter the correct range！')
          return
        }
        if(end>this.bpLength){
          this.$message.warning(this.language==='zh'?'请输入正确的范围':'Please enter the correct range！')
          return
        }
                selection.start = start-1
        selection.end = end-1
        if(start>this.bpLength-this.sequenceLengthToShow){
          this.currentStart = this.bpLength-this.sequenceLengthToShow
        }else{
          this.currentStart = start
        }
        queryParam.start = this.currentStart
        if(this.bpLength>this.sequenceLengthToShow){
          const percent = this.currentStart / this.bpLength
          this.sliderLeft = this.rangeWidth * percent
        }
      }
      this.loadSequence(queryParam,selection)
    }
  },
  watch: {
    bpLength(val) {
      this.calSliderWidth()
    }
  }
}
</script>
<style  lang="scss">
@import "@/assets/styles/variables.scss";
.range-f {
  width: 100%;
  height: 55px;
  background-color: #fff;
  padding: 10px;
  input[type="range"] {
    display: block;
    -webkit-appearance: none;
    width: 100%;
    height: 36px;
    border-radius: 10px;
    margin: 0 auto;
    outline: 0;
  }
  input[type="range"]::-webkit-slider-thumb {
    -webkit-appearance: none;
    width: attr(data-width);
    height: 36px;
    border: 3px solid #ff2526;
    cursor: pointer;
    transition: 0.3s ease-in-out;

  }
  .range {
    position: relative;
    width: 100%;
    margin: 0 auto;
    background-color: #bdc3c7;
        height: 36px;
    overflow: hidden;
    border-radius: 4px;
    .slider {
      user-select:none;
      position: absolute;
      height: 36px;
            top: 0;
      cursor: pointer;
      background-color: $base-color;
      border-color: $base-color;
      background-image: -webkit-linear-gradient(top, rgba(255,255,255,.3), rgba(255,255,255,0));
      border-radius: 4px;
      text-shadow: 0 1px 0 rgba(0,0,0,.2);
      display: flex;
      justify-content: center;
      align-items: center;
      color: #148b06;
    }
  }
  .rang_width {
    position: absolute;
    top: -15px;
    left: 0;
    background: #f00;
    height: 10px;
    border-radius: 5px 0 0 5px;
  }
  .range-f-number {
    display: flex;
    justify-content: space-between;
  }
}
.slider-number {
  position: relative;
  height: 10px;
  div {
    position: absolute;
    left: 0;
    top: 0;
    width: 100px;
  }
}
.choose{
  cursor: pointer;
  background-color:#fff;padding: 0;border:1px solid #eee;
  .left-choose{
    background-color: rgb(235, 241,255);
    color: $base-color;
  }
  .left{
    font-size: 14px;
    white-space: nowrap;
    text-overflow:ellipsis;
    overflow:hidden;
    padding: 0 20px;
    line-height: 34px;
    margin: 5px;
  }
  .left:hover{
    background-color: rgb(245, 247, 250);
  }
}
.search-f {
  height: 55px;
  display: flex;
  justify-content: center;
  align-items: center;
  .search {
    display: flex;
    align-items: center;
    .search-left {
      font-weight: 500;
      font-size: 20px;
      margin-right: 5px;
      width: 200px;
      overflow: hidden;
      text-align: right;
    }
    .center {
      display: flex;
      align-items: center;
      .center-children-2 {
        display: flex;
        align-items: center;
        border-top: 1px solid #e4e4e4;
        border-bottom: 1px solid #e4e4e4;
        padding: 0 15px;
        height: 28px;
      }
    }
    .vxe-input{
      height: 28px;
      line-height: 28px;
    }
    .vxe-input--inner{
      border-radius: 0;
    }
  }
}
</style>
