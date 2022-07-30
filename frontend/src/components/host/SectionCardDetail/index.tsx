import useSectionCardDetail from './useSectionCardDetail';
import { BiNews } from 'react-icons/bi';

import Button from '@/components/common/Button';

import { SectionType } from '@/types';

import styles from './styles';

interface SectionCardDetailProps {
  section: SectionType;
  sectionIndex: number;
}

const SectionCardDetail: React.FC<SectionCardDetailProps> = ({ section, sectionIndex }) => {
  const { onClickSectionDetail, onClickTaskDetail } = useSectionCardDetail();

  return (
    <>
      <div css={styles.titleWrapper}>
        <span>{section.name}</span>
        <div>
          <Button css={styles.detailButton} onClick={() => onClickSectionDetail(sectionIndex)}>
            상제 정보 추가
          </Button>
        </div>
      </div>
      {section.tasks.map((task, taskIndex) => (
        <div css={styles.taskWrapper} key={task.id}>
          <span>∙ {task.name}</span>
          <BiNews size={22} onClick={() => onClickTaskDetail(taskIndex)} />
        </div>
      ))}
    </>
  );
};

export default SectionCardDetail;
