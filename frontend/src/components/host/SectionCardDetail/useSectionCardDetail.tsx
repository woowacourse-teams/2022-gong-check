import SectionDetailModal from '../SectionDetailModal';

import useModal from '@/hooks/useModal';
import useSections from '@/hooks/useSections';

const useSectionCardDetail = (sectionIndex: number) => {
  const { openModal } = useModal();
  const { getSectionInfo, getTaskInfo } = useSections();

  const hasSectionDetailInfo = (sectionIndex: number) => {
    const { imageUrl, description } = getSectionInfo(sectionIndex);
    return !!imageUrl || !!description;
  };

  const hasTaskDetailInfo = (sectionIndex: number, taskIndex: number) => {
    const { imageUrl, description } = getTaskInfo(sectionIndex, taskIndex);
    return !!imageUrl || !!description;
  };

  const onClickSectionDetail = (sectionIndex: number) => {
    const previousImageUrl = getSectionInfo(sectionIndex).imageUrl;
    const previousDescription = getSectionInfo(sectionIndex).description;

    openModal(
      <SectionDetailModal
        target="section"
        sectionIndex={sectionIndex}
        previousImageUrl={previousImageUrl}
        previousDescription={previousDescription}
      />
    );
  };

  const onClickTaskDetail = (taskIndex: number) => {
    const previousImageUrl = getTaskInfo(sectionIndex, taskIndex).imageUrl;
    const previousDescription = getTaskInfo(sectionIndex, taskIndex).description;

    openModal(
      <SectionDetailModal
        target="task"
        sectionIndex={sectionIndex}
        taskIndex={taskIndex}
        previousImageUrl={previousImageUrl}
        previousDescription={previousDescription}
      />
    );
  };

  return {
    onClickSectionDetail,
    onClickTaskDetail,
    hasSectionDetailInfo,
    hasTaskDetailInfo,
  };
};

export default useSectionCardDetail;
