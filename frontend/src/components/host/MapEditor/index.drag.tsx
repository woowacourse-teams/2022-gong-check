import { css } from '@emotion/react';
import { forwardRef, useRef, useState } from 'react';

import styles from './styles';

const SECTIONS = ['대강의실', '소강의실', '페어룸'];

const MapEditor = () => {
  const [dragElements, setDragElements] = useState<string[]>([]);
  const dragRef = useRef<any>([]);
  const [currentTarget, setCurrentTarget] = useState<any>();

  const onClickCircle = () => {
    setDragElements(prev => [...prev, 'Circle']);
  };

  const onClickSquare = () => {
    setDragElements(prev => [...prev, 'Square']);
  };

  function drop_handler(event: any) {
    //ondrop = draggable 엘리먼트를 drop영역위에 떨어트리면
    // event.preventDefault();

    console.log(event);
    console.log(dragRef.current[currentTarget]);
    dragRef.current[currentTarget].style.top = event.clientY - 40 + 'px';
    dragRef.current[currentTarget].style.left = event.clientX - 40 + 'px';
  }

  const onDragStart = (event: any, index: any) => {
    event.currentTarget.style.opacity = 0.4;
    setCurrentTarget(index);
  };

  const onDragOver = (event: any) => {
    event.preventDefault();
  };

  const onDragEnd = (event: any) => {
    event.currentTarget.style.opacity = '1';
  };

  return (
    <div css={styles.layout}>
      <div css={styles.editor}>
        <div css={styles.left}>
          <Circle onClick={onClickCircle} />
        </div>
        <div css={styles.right}>
          <div css={styles.sectionList}>
            {SECTIONS.map((section, index) => (
              <div className="section" key={index}>
                {section}
              </div>
            ))}
          </div>
          <div css={styles.playGround} onDrop={drop_handler} onDragOver={onDragOver}>
            {dragElements.map((dragElement, index) => (
              <Draggable
                key={index}
                index={index}
                onDragStart={(e: any) => onDragStart(e, index)}
                onDragEnd={onDragEnd}
                ref={(el: any) => (dragRef.current[index] = el)}
              >
                <Circle onClick={onClickCircle} />
              </Draggable>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

const Circle = ({ onClick }: any) => {
  return (
    <div
      css={css`
        width: 80px;
        height: 80px;
        border-radius: 100%;
        background-color: skyblue;
        margin: 8px 0;
        cursor: pointer;
      `}
      onClick={onClick}
    />
  );
};

const Draggable = forwardRef(({ children, index, onDragStart, onDragEnd }: any, ref: any) => {
  return (
    <div
      css={css`
        width: fit-content;
        height: fit-content;
        position: absolute;
      `}
      onDragStart={onDragStart}
      draggable="true"
      ref={ref}
      onDragEnd={onDragEnd}
      onDragEnter={event => {
        console.log('요소 겹침');
        event.preventDefault();
      }}
    >
      {children}
    </div>
  );
});

export default MapEditor;
