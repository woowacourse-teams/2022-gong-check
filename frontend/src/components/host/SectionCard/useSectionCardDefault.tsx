import SectionDetailModal from '../SectionDetailModal';

import useModal from '@/hooks/useModal';
import useSections from '@/hooks/useSections';

const useSectionCardDefault = (sectionIndex: number) => {
  const { openModal } = useModal();

  const { editSection, deleteSection, createTask, getSectionInfo } = useSections();

  const hasSectionDetailInfo = () => {
    const { imageUrl, description } = getSectionInfo(sectionIndex);
    return !!imageUrl || !!description;
  };

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    editSection(sectionIndex, e.target.value);
  };

  const onClickDelete = () => {
    deleteSection(sectionIndex);
  };

  const onClickSectionDetail = () => {
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

  const onClickCreate = () => {
    createTask(sectionIndex);
  };

  return { onChange, onClickDelete, onClickCreate, onClickSectionDetail, hasSectionDetailInfo };
};

export default useSectionCardDefault;
