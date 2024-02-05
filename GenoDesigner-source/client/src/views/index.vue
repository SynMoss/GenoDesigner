<template>
  <div class="index">
    <div style="display: flex; height: 100%">
      <div class="index-right">
        <div class="index-right-top">
          <div class="title">Genome Design</div>
          <div class="desc">
            Your recently created design is displayed here for quick access. The system saves a maximum of 10 design records, and you can also download or delete past designs
          </div>
        </div>
        <div class="index-right-title">
          <span class="el-icon-time"></span> Recently viewed Design
        </div>
        <div
          class="index-right-bottom-parent"
          :element-loading-text="language==='zh'?zh_list.B:'Loading...'"
          element-loading-spinner="el-icon-loading"
          v-loading="loading"
        >
          <div class="index-right-bottom">
            <div class="init-programmer" v-show="viewedDesigns.length == 0">
              <div>
                <el-empty :description="language==='zh'?zh_list.C:'There is currently no data available'"></el-empty>
              </div>
            </div>
            <div
              class="index-right-bottom-cell"
              v-for="item in viewedDesigns"
              :key="item.id"
              :label="item.programmeName"
              :value="item.id"
            >
              <div class="index-right-bottom-cell-left">
                <a @click="toInfo(item.id)">{{ item.programmeName }}</a>
              </div>
              <div class="index-right-bottom-cell-right">
                {{ item.viewedTime }}
              </div>
            </div>
          </div>
        </div>
      </div>
      <div style="flex: 0 0 20px"></div>
      <div class="index-left" ref="indexLeft">
        <div ref="indexCard" class="card" @click="newProgramme" data-intro="Step 1: Create a new design" data-step="1">
          <div class="top">
            <p>Create a new design</p>
            <img src="@/assets/images/g1.png" alt="" />
          </div>
          <div class="bottom">
            <p>Quick create design</p>
          </div>
        </div>
        <div ref="indexCard" class="card" @click="introStart">
          <div class="top">
            <p>operational guideline</p>
            <img src="@/assets/images/g2.png" alt="" />
          </div>
          <div class="bottom">
            <p>This chapter describes how to use the software</p>
          </div>
        </div>
        <div class="card" target="_blank" @click="toManual()">
          <div class="top">
            <p>Help manual</p>
            <img src="@/assets/images/g3.png" alt="" />
          </div>
          <div class="bottom">
            <p>Read the manual to find out more about how to use it</p>
          </div>
        </div>
          <div ref="indexCard" class="card" @click="newProgramme" >
            <div class="top">
              <p>Create a new design</p>
              <img src="@/assets/images/g1.png" alt="" />
            </div>
            <div class="bottom">
              <p>Quick create design</p>
            </div>
          </div>
          <div ref="indexCard" class="card" @click="introStart">
            <div class="top">
              <p>operational guideline</p>
              <img src="@/assets/images/g2.png" alt="" />
            </div>
            <div class="bottom">
              <p>This chapter describes how to use the software</p>
            </div>
          </div>
          <div class="card" target="_blank" @click="toManual()">
            <div class="top">
              <p>Help manual</p>
              <img src="@/assets/images/g3.png" alt="" />
            </div>
            <div class="bottom">
              <p>Read the manual to find out more about how to use it</p>
            </div>
          </div>
      </div>
    </div>
    <new-programme ref="newProgramme" @ok="newProgrammeOk"></new-programme>
  </div>
</template>
<script>
import NewProgramme from "./programme/modules/ProgrammeDialog";
import { listViewedProgrammes } from "@/api/system/programme";
export default {
  name: "index",
  data() {
    return {
      language: process.env.VUE_APP_LANGUAGE,
      viewedDesigns: [],
      loading: false,
    };
  },
  components: {
    NewProgramme,
  },
  computed: {},
  watch: {},
  created() {
    this.selectViewedDesigns();
  },
  mounted() {},
  beforeDestroy() {},
  methods: {
    newProgrammeOk() {},
    newProgramme(){
      this.$refs.newProgramme.showDialog()
    },
    selectViewedDesigns() {
      this.loading = true;
      listViewedProgrammes().then((res) => {
        this.viewedDesigns = res.data;
        this.loading = false;
      });
    },
    introStart(){
      this.$intro()
      .setOption("nextLabel", this.zh_list.nextstep)
      .setOption("prevLabel", this.zh_list.laststep)
      .setOption("doneLabel", this.zh_list.accomplish)
      .start();
    },
    toManual() {
      const url =
        "http://mozilla.github.io/pdf.js/web/compressed.tracemonkey-pldi-09.pdf";
      window.open(url, "_blank");
    },
    toInfo(id) {
      this.$router.push({ path: "/genBlackDesignInfo", query: { id: id } });
    },
  },
};
</script>
<style></style>
<style lang="scss" scoped>
@import "@/assets/styles/variables.scss";
.index {
  height: calc(100vh - 50px);
  padding: 20px;
  .index-right {
    background-color: #fff;
    padding: 20px;
    flex: auto;
    display: flex;
    flex-direction: column;
    .index-right-top {
      border-left: 5px solid $base-color;
      padding: 30px;
      background: #f9faff;
      .title {
        font-size: 20px;
        margin-bottom: 10px;
      }
      .desc {
        font-size: 14px;
        line-height: 20px;
      }
    }
    .index-right-title {
      font-size: 16px;
      font-weight: 500;
      color: #333333;
      line-height: 22px;
      padding: 20px 30px;
      border-bottom: 1px solid #ededed;
      margin-bottom: 20px;
      position: relative;
      .openicon {
        width: 24px;
        height: 24px;
        display: inline-block;
        background: url(../assets/images/time-fill.svg);
        position: absolute;
        left: 2px;
        top: 18px;
      }
    }
    .index-right-bottom-parent {
      position: relative;
      flex: auto;
      .index-right-bottom {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        .index-right-bottom-cell {
          display: flex;
          justify-content: space-between;
          margin-bottom: 20px;
          .index-right-bottom-cell-left {
            font-size: 14px;
            font-weight: 500;
            color: #666666;
            line-height: 40px;
            flex: 0 0 50%;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
          }
          .index-right-bottom-cell-right {
            font-size: 14px;
            font-weight: 500;
            text-align: left;
            color: #999999;
            line-height: 40px;
          }
        }
      }
    }
  }
  .index-left {
    height: 100%;
    flex: 0 0 38%;
    display: flex;
    flex-direction: column;
    background-color: #fff;
    padding: 20px;
    display: flex;
    flex-direction: column;
    .card {
      border: 1px solid #e7ebff;
      border-radius: 4px;
      flex: 0 0 33%;
      display: flex;
      flex-direction: column;
      box-sizing: border-box;
      overflow: hidden;
      cursor: pointer;
      margin-bottom: 10px;
      .top {
        flex: 0 0 60%;
        position: relative;
        background: linear-gradient(90deg, #f3f9fc, #9fa8b3);
        p {
          position: absolute;
          left: 20px;
          bottom: 15%;
          font-size: 20px;
          font-weight: 600;
          color: #333333;
          line-height: 28px;
        }
        img {
          position: absolute;
          bottom: 0;
          right: 30px;
          height: 76%;
        }
      }
      .bottom {
        p {
          font-size: 14px;
          font-weight: 600;
          color: #999999;
          line-height: 20px;
          padding: 11px 16px;
        }
        flex: 0 0 40%;
      }
    }
    .card:hover {
      border-color: $base-color;
      box-shadow: 0px 0px 0px 1px $base-color;
    }
    .title {
      flex: 0 0 42px;
      font-size: 16px;
      font-weight: 600;
      color: #333333;
      line-height: 22px;
      position: relative;
      padding-left: 30px;
      .starticon {
        width: 24px;
        height: 24px;
        display: inline-block;
        background: url(../assets/images/play-circle-fill.svg);
        position: absolute;
        left: 2px;
        top: -1px;
      }
      .helpicon {
        background: url(../assets/images/question-fill.svg);
      }
    }
  }
}
</style>
