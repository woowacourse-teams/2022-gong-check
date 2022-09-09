import useTaskList from './useTaskList';
import React from 'react';
import { IoIosArrowBack } from 'react-icons/io';

import Button from '@/components/common/Button';
import SectionInfoPreview from '@/components/user/SectionInfoPreview';
import TaskCard from '@/components/user/TaskCard';

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
    progressBarRef,
    isActiveSticky,
  } = useTaskList();

  return (
    <div css={styles.layout}>
      <div css={styles.header}>
        <div css={styles.arrowBackIconWrapper}>
          <IoIosArrowBack size={30} onClick={goPreviousPage} />
        </div>
        <div css={styles.thumbnail(spaceData?.imageUrl || DEFAULT_IMAGE)} />
        <div css={styles.infoWrapper}>
          <p>{spaceData?.name}</p>
          <p>{locationState?.jobName}</p>
        </div>
      </div>
      <div css={styles.progressBarWrapperSticky(isActiveSticky)} ref={progressBarRef}>
        <div css={styles.progressBarWrapper}>
          <div css={styles.progressBar(percent)} />
          <span css={styles.percentText}>{`${checkedCount}/${totalCount}`}</span>
        </div>
      </div>
      <div css={styles.contents}>
        <form css={styles.form} onSubmit={onSubmit}>
          {sectionsData?.sections.map(section => (
            <section css={styles.location} key={section.id!}>
              <div css={styles.locationHeader}>
                <p css={styles.locationName}>{section.name}</p>
                <div css={styles.locationHeaderRightItems}>
                  {!sectionsAllCheckMap.get(`${section.id}`) && (
                    <Button
                      css={styles.sectionAllCheckButton}
                      type="button"
                      onClick={() => onClickSectionAllCheck(section.id!)}
                    >
                      ALL
                    </Button>
                  )}
                  {(section.imageUrl || section.description) && (
                    <SectionInfoPreview imageUrl={section.imageUrl} onClick={() => onClickSectionDetail(section)} />
                  )}
                </div>
              </div>
              <TaskCard tasks={section.tasks} />
            </section>
          ))}
          <Button type="submit" css={styles.button(isAllChecked)} disabled={!isAllChecked}>
            제출
          </Button>
        </form>
      </div>
    </div>
  );
};
export default TaskList;
