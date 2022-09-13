import useTaskList from './useTaskList';
import { css } from '@emotion/react';
import React from 'react';
import { IoIosArrowBack } from 'react-icons/io';

import Button from '@/components/common/Button';
import Sticky from '@/components/common/Sticky';
import SectionCard from '@/components/user/SectionCard';

import DEFAULT_IMAGE from '@/assets/defaultSpaceImage.webp';

import styles from './styles';

const TaskList: React.FC = () => {
  const {
    spaceData,
    onSubmit,
    goPreviousPage,
    totalCount,
    checkedCount,
    percent,
    sectionsAllCheckMap,
    isAllChecked,
    locationState,
    sectionsData,
    onClickSectionDetail,
    onClickSectionAllCheck,
  } = useTaskList();

  return (
    <div css={styles.layout}>
      <div css={styles.header}>
        <div css={styles.arrowBackIcon}>
          <IoIosArrowBack size={30} onClick={goPreviousPage} />
        </div>
        <div css={styles.headerInfo}>
          <div css={styles.thumbnail(spaceData?.imageUrl || DEFAULT_IMAGE)} />
          <div css={styles.infoWrapper}>
            <p>{spaceData?.name}</p>
            <p>{locationState?.jobName}</p>
          </div>
        </div>
      </div>
      <Sticky defaultPosition={232}>
        <div css={styles.progressBarWrapper}>
          <div css={styles.progressBar(percent)} />
          <span css={styles.percentText}>{`${checkedCount}/${totalCount}`}</span>
        </div>
      </Sticky>

      <form css={styles.contents} onSubmit={onSubmit}>
        {sectionsData?.sections.map(section => (
          <SectionCard
            key={section.id}
            section={section}
            sectionsAllCheckMap={sectionsAllCheckMap}
            onClickSectionDetail={onClickSectionDetail}
            onClickSectionAllCheck={onClickSectionAllCheck}
          />
        ))}
        <Button type="submit" css={styles.button(isAllChecked)} disabled={!isAllChecked}>
          제출
        </Button>
      </form>
    </div>
  );
};
export default TaskList;
