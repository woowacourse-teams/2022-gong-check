import { css } from '@emotion/react';

type PageTitleProps = {
  children: React.ReactNode;
};

const PageTitle = ({ children }: PageTitleProps) => {
  return (
    <p
      css={css`
        margin: 24px 0;
        font-size: 20px;
        font-weight: 600;
      `}
    >
      {children}
    </p>
  );
};

export default PageTitle;
