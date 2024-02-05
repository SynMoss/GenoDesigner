import { createVNode, render } from 'vue';
import SelectAreaConstructor from './index.vue';
let instence
let instenceIsExit = false;
const SelectArea = function(options) {
  if (instenceIsExit) {
    document.body.removeChild(instence);
    instenceIsExit = false;
  }
  const vm = createVNode(SelectAreaConstructor, options);
  const container = document.createElement('div');
  render(vm, container);
  instence = container.firstElementChild;
  document.body.appendChild(instence);
  instenceIsExit = true;
  return instence;
};

const close = () => {
  if (instenceIsExit) {
        instenceIsExit = false;
    document.body.removeChild(instence);
    instence = undefined;
  }
};
function selectElement(
  parentElement,
  selectBoxElement,
  keyCode
) {
  if (keyCode) {
  }
  const canCheckedElements = getChildrens(parentElement, keyCode);
  const containElements = judgeContainElement(
    selectBoxElement,
    canCheckedElements
  );
  return {
    containElements,
    canCheckedElements,
  };
}
function getChildrens(parentElement, keyCode) {
  const ary = [];
  const childs = parentElement.childNodes;
  for (let i = 0; i < childs.length; i++) {
    if (childs[i].nodeType === 1) {
      if ((childs[i]).getAttribute(keyCode) !== null) {
        ary.push(childs[i]);
      }
    }
  }
  return ary;
}
function judgeContainElement(
  selectBoxElement,
  canCheckedElements
) {
  const ContainElement = [];
  const { left, right, bottom, top } = selectBoxElement.getBoundingClientRect();
  canCheckedElements.forEach((item) => {
    const child = item.getBoundingClientRect();
    if (
      child.left > left &&
      child.top > top &&
      child.bottom < bottom &&
      child.right < right
    ) {
      ContainElement.push(item);
    }
  });
  return ContainElement;
}
export { SelectArea, close,selectElement };
