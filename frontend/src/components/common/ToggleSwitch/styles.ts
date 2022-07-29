import { css } from '@emotion/react';

import theme from '@/styles/theme';

const wrapper = css`
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 4px;
  background-color: ${theme.colors.shadow20};
  border-radius: 15px;
  width: 120px;
  height: 30px;
  position: relative;
  cursor: pointer;
`;

const element = (selector: boolean) => css`
  width: 50%;
  border-radius: 15px;
  text-align: center;
  font-size: 12px;
  color: ${selector && theme.colors.shadow50};
  transition: all 0.3s ease-in-out;
  z-index: 1;
`;

const ball = (toggle: boolean) => css`
  background-color: white;
  width: 46%;
  height: 80%;
  border-radius: 12px;
  position: absolute;
  left: 5%;
  transition: all 0.3s ease-in-out;
  box-shadow: 2px 2px 2px 0px ${theme.colors.shadow30};
  ${toggle &&
  css`
    transform: translate(100%, 0);
    transition: all 0.3s ease-in-out;
  `}
`;

const styles = { wrapper, element, ball };

export default styles;
