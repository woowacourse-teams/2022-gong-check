import { css } from '@emotion/react';
import { forwardRef, useRef, useState } from 'react';

const Draggable = ({
  children,
  index,
  updateEditingIndex,
  resetClickedElements,
  addClickedElements,
  isClicked,
}: any) => {
  const onStart = (e: any) => {
    updateEditingIndex(index);
    let isPress = true,
      prevPosX = e.clientX,
      prevPosY = e.clientY;

    const targetElement = e.currentTarget;
    const parentElement = e.currentTarget.parentElement;

    targetElement.onmouseup = end;

    parentElement.onmousemove = move;
    parentElement.onmouseleave = end;
    parentElement.onclick = (e: any) => {
      if (parentElement !== e.target) return;
      console.log('clicked');
      resetClickedElements();
    };

    const parentPosition = {
      top: parentElement.offsetTop,
      right: parentElement.offsetLeft + parentElement.offsetWidth,
      bottom: parentElement.offsetTop + parentElement.offsetHeight,
      left: parentElement.offsetLeft,
    };

    function move(e: any) {
      if (!isPress) return;
      if (isClicked) return;

      const posX = prevPosX - e.clientX;
      const posY = prevPosY - e.clientY;

      prevPosX = e.clientX;
      prevPosY = e.clientY;

      let newTop = targetElement.offsetTop - posY,
        newLeft = targetElement.offsetLeft - posX;

      if (targetElement.offsetLeft - posX <= parentPosition.left) newLeft = parentPosition.left;

      if (targetElement.offsetTop - posY <= parentPosition.top) newTop = parentPosition.top;

      if (targetElement.offsetTop - posY + targetElement.clientHeight >= parentPosition.bottom)
        newTop = parentPosition.bottom - targetElement.clientHeight;

      if (targetElement.offsetLeft - posX + targetElement.clientWidth > parentPosition.right)
        newLeft = parentPosition.right - targetElement.clientWidth;

      targetElement.style.left = newLeft + 'px';
      targetElement.style.top = newTop + 'px';
    }

    function end() {
      isPress = false;
      parentElement.onmouseleave = null;
    }
  };

  return (
    <div
      css={css`
        width: fit-content;
        height: fit-content;
        position: absolute;
        overflow: auto;
      `}
      onMouseDown={onStart}
      onDoubleClick={e => {
        addClickedElements(index, true);
      }}
    >
      {children}
    </div>
  );
};

export default Draggable;
