import useTaskList from './useTaskList';
import { IoIosArrowBack } from 'react-icons/io';

import Button from '@/components/common/Button';
import SectionInfoPreviewBox from '@/components/user/SectionInfoPreviewBox';
import TaskCard from '@/components/user/TaskCard';

import useSectionCheck from '@/hooks/useSectionCheck';

import styles from './styles';

const TaskList: React.FC = () => {
  const { locationState, sectionsData, spaceData, getSections, onClickButton, goPreviousPage, onClickSectionDetail } =
    useTaskList();

  if (!sectionsData || !spaceData) return <></>;

  const { totalCount, checkCount, percent, isAllChecked } = useSectionCheck(sectionsData.sections);

  return (
    <div css={styles.layout}>
      <div css={styles.header}>
        <div css={styles.arrowBackIconWrapper}>
          <IoIosArrowBack size={30} onClick={goPreviousPage} />
        </div>
        <div css={styles.thumbnail(spaceData.imageUrl)} />
        <div css={styles.infoWrapper}>
          <p>{spaceData.name}</p>
          <p>{locationState?.jobName}</p>
        </div>
      </div>
      <div css={styles.progressBarWrapper}>
        <div css={styles.progressBar(percent || 0)} />
        <span css={styles.percentText}>{`${checkCount}/${totalCount}`}</span>
      </div>
      <div css={styles.contents}>
        <form css={styles.form}>
          {sectionsData.sections.length === 0 ? (
            <div>체크리스트가 없습니다.</div>
          ) : (
            sectionsData.sections.map(section => (
              <section css={styles.location} key={section.id}>
                <div css={styles.locationHeader}>
                  <p css={styles.locationName}>{section.name}</p>
                  {/* TODO: 해당 section에 상세 정보 유무에 따라 SectionInfoPreviewBox 노출해야한다.*/}
                  <SectionInfoPreviewBox
                    imageUrl={spaceData.imageUrl}
                    onClick={() =>
                      onClickSectionDetail({
                        name: section.name,
                        imageUrl: spaceData.imageUrl,
                        description: '어쩌고 저쩌고 이렇고 저렇고....아아아아콜라',
                      })
                    }
                  />
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
