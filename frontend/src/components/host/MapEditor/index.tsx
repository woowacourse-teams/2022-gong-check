import Draggable from './Draggable';
import { css } from '@emotion/react';
import { useRef, useState } from 'react';

import useSections from '@/hooks/useSections';

import styles from './styles';

const SECTIONS = ['대강의실', '소강의실', '페어룸'];

const MapEditor = () => {
  const [dragElements, setDragElements] = useState<string[]>([]);

  const { sections, createSection, resetSections } = useSections();

  const [draggedIndex, setDraggedIndex] = useState<any>();
  const [clickedElements, setClickedElements] = useState<any>(new Map());

  const onClickCircle = () => {
    setDragElements(prev => [...prev, 'Circle']);
  };

  const onClickSquare = () => {
    setDragElements(prev => [...prev, 'Square']);
  };

  const updateEditingIndex = (index: any) => {
    setDraggedIndex(index);
  };

  const resetClickedElements = () => {
    setClickedElements((prev: any) => new Map(prev.clear()));
  };

  const addClickedElements = (index: any, clicked: any) => {
    setClickedElements((prev: any) => new Map(prev).set(index, clicked));
  };

  return (
    <div css={styles.layout}>
      <div css={styles.editor}>
        <div css={styles.left}>
          <Circle onClick={onClickCircle} />
          <Square onClick={onClickSquare} />
        </div>
        <div css={styles.right}>
          <div css={styles.sectionList}>
            {sections.map((section, index) => (
              <div
                css={css`
                  width: 68px;
                  height: 32px;
                  border-radius: 16px;
                  background-color: aquamarine;
                  text-align: center;
                  line-height: 32px;
                  margin: 0 12px;
                `}
                key={index}
              >
                {section.name}
              </div>
            ))}
          </div>
          <div css={styles.playGround}>
            {dragElements.map((dragElement, index) => (
              <Draggable
                key={index}
                index={index}
                updateEditingIndex={updateEditingIndex}
                resetClickedElements={resetClickedElements}
                addClickedElements={addClickedElements}
                isClicked={clickedElements.get(index)}
              >
                {dragElement === 'Circle' ? (
                  <Circle
                    isDragged={draggedIndex === index}
                    isClicked={clickedElements.get(index)}
                    clickedElements={clickedElements}
                  />
                ) : (
                  <Square isDragged={draggedIndex === index} isClicked={clickedElements.get(index)} />
                )}
              </Draggable>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MapEditor;

const Circle = ({ onClick, isDragged, isClicked }: any) => {
  return (
    <div
      css={css`
        width: 80px;
        height: 80px;
        border-radius: 100%;
        background-color: skyblue;
        cursor: ${isDragged ? 'move' : 'pointer'};
        ${isClicked && 'background-color: white;'}
        ${isClicked && 'overflow: auto; resize: both;'}
      `}
      onClick={onClick}
    />
  );
};

const Square = ({ onClick, isDragged, isClicked }: any) => {
  return (
    <div
      css={css`
        width: 80px;
        height: 80px;
        background-color: tomato;
        cursor: ${isDragged ? 'move' : 'pointer'};
        ${isClicked && 'background-color: white;'}
        ${isClicked && 'overflow: auto; resize: both;'}
      `}
      onClick={onClick}
    />
  );
};

const EditCircle = ({ onClick }: any) => {
  return (
    <div
      css={css`
        width: 80px;
        height: 80px;
        border-radius: 100%;
        background-color: skyblue;

        cursor: pointer;
      `}
      onClick={onClick}
    />
  );
};

const EditSquare = ({ onClick }: any) => {
  return (
    <div
      css={css`
        width: 80px;
        height: 80px;
        background-color: tomato;
        margin: 8px 0;
        cursor: pointer;
      `}
      onClick={onClick}
    />
  );
};
