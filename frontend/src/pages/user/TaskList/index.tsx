import useTaskList from './useTaskList';
import { useEffect, useRef } from 'react';
import { IoIosArrowBack } from 'react-icons/io';

import Button from '@/components/common/Button';
import SectionInfoPreviewBox from '@/components/user/SectionInfoPreviewBox';
import TaskCard from '@/components/user/TaskCard';

import styles from './styles';

const TaskList: React.FC = () => {
  const {
    spaceData,
    getSections,
    onClickButton,
    goPreviousPage,
    totalCount,
    checkCount,
    percent,
    isAllChecked,
    locationState,
    sectionsData,
    onClickSectionDetail,
    progressBarRef,
    isSticked,
  } = useTaskList();

  return (
    <div css={styles.layout}>
      <div css={styles.header}>
        <div css={styles.arrowBackIconWrapper}>
          <IoIosArrowBack size={30} onClick={goPreviousPage} />
        </div>
        <div css={styles.thumbnail(spaceData?.imageUrl)} />
        <div css={styles.infoWrapper}>
          <p>{spaceData?.name}</p>
          <p>{locationState?.jobName}</p>
        </div>
      </div>
      <div css={styles.progressBarWrapperSticky(isSticked)} ref={progressBarRef}>
        <div css={styles.progressBarWrapper}>
          <div css={styles.progressBar(percent || 0)} />
          <span css={styles.percentText}>{`${checkCount}/${totalCount}`}</span>
        </div>
      </div>
      <div css={styles.contents}>
        <form css={styles.form}>
          {sectionsData?.sections.length === 0 ? (
            <div>체크리스트가 없습니다.</div>
          ) : (
            sectionsData?.sections.map(section => (
              <section css={styles.location} key={section.id}>
                <div css={styles.locationHeader}>
                  <p css={styles.locationName}>{section.name}</p>
                  {(section.imageUrl || section.description) && (
                    <SectionInfoPreviewBox
                      imageUrl={section.imageUrl}
                      onClick={() =>
                        onClickSectionDetail({
                          name: section.name,
                          imageUrl: section.imageUrl,
                          description: section.description,
                        })
                      }
                    />
                  )}
                </div>
                <TaskCard tasks={section.tasks} getSections={getSections} />
              </section>
            ))
          )}
          <Button
            type="submit"
            css={styles.button(isAllChecked || false)}
            onClick={onClickButton}
            disabled={!isAllChecked}
          >
            제출
          </Button>
        </form>
      </div>
    </div>
  );
};
export default TaskList;
