<template>
  <div class="app-container home">
    <div
      id="select-area"
      class="select-area"
      :style="[
        { width: size.width + 'px' },
        { height: size.height + 'px' },
        { top: point.y + 'px' },
        { left: point.x + 'px' },
      ]"
    ></div>
  </div>
</template>

<script>
export default {
  name: 'SelectArea',
  data() {
    return {}
  },
  props: {
    startPoint: {
      type: Object,
      default: null
    },
    endPoint: {
      type: Object,
      default: null
    }
  },
  created() {},
  mounted() {},
  computed: {
    point() {
      const x =
        this.endPoint.x === 0
          ? this.startPoint.x
          : Math.min(this.startPoint.x, this.endPoint.x)
      const y =
        this.endPoint.y === 0
          ? this.startPoint.y
          : Math.min(this.startPoint.y, this.endPoint.y)
      return {
        x,
        y
      }
    },
    size() {
      const width =
        this.endPoint.x === 0
          ? 0
          : Math.abs(this.startPoint.x - this.endPoint.x)
      const height =
        this.endPoint.y === 0
          ? 0
          : Math.abs(this.startPoint.y - this.endPoint.y)
      return {
        width,
        height
      }
    }
  },
  methods: {}
}
</script>

<style scoped lang="scss">
.select-area {
  position: fixed;
  background-color: rgba(255, 192, 203, 0.1);
  border: 1px solid red;
  z-index: 9;
}
</style>

