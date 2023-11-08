package lotto.service;

import static lotto.constants.LottoConstants.LOTTO_SIZE_MAX_LENGTH;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lotto.dto.LottoDto;
import lotto.exception.ErrorMsg;
import lotto.exception.UserInputException;

public class LottoService {

    public List<Integer> createWinningNumbers(String winningInputNumbers) {
        List<Integer> winningNumbers = winningNumberSplitter(winningInputNumbers);
        List<Integer> validateCheckOfWinningNumber = duplicatesWinningNumbers(winningNumbers);
        return new ArrayList<>(validateCheckOfWinningNumber);
    }

    public int createBonusNumber(List<Integer> generateLottoNumbers, String commonInput) {
        while (true) {
            try {
                validateBonusNumberCheck(generateLottoNumbers, Integer.parseInt(commonInput));
                return Integer.parseInt(commonInput);
            } catch (UserInputException e) {
                System.out.println(ErrorMsg.ERROR_LOTTO_DUPLICATES_NUMBERS.getMsg());
            } catch (NumberFormatException e) {
                System.out.println(ErrorMsg.ERROR_INPUT_NOT_NUMBER.getMsg());
            }
            commonInput = Console.readLine();
        }

    }

    public Map<Integer, Integer> findWinners(List<Integer> lottoWinningNumbers, int lottoBonusNumber,
            List<LottoDto> generateLottoNumbersDto) {
        List<Map<Integer, Integer>> lottoMatchCount = new ArrayList<>();
        for (LottoDto lottoDto : generateLottoNumbersDto) {
//            Map<Integer, Integer> matchCount = lottoDto.matchCount(lottoWinningNumbers, lottoBonusNumber);
//            lottoMatchCount.add(matchCount);
        }
        return topCount(lottoMatchCount);
    }

    public List<Integer> duplicatesWinningNumbers(List<Integer> winningNumbers) {
        while (true) {
            Set<Integer> winningNumberDuplicates = new HashSet<>(winningNumbers);
            try {
                validateDuplicateWinningNumber(winningNumbers.size(), winningNumberDuplicates.size());
                return winningNumbers;
            } catch (UserInputException e) {
                System.out.println(ErrorMsg.ERROR_LOTTO_DUPLICATES_NUMBERS.getMsg());
            }
            String userRetryInput = Console.readLine();
            List<Integer> retryNumberFormat = winningNumberSplitter(userRetryInput);
            winningNumbers = retryNumberFormat;
        }
    }

    public List<Integer> winningNumberSplitter(String winningInputNumbers) {
        while (true) {
            try {
                List<Integer> winningNumbers = Arrays.stream(winningInputNumbers.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                validateWinningNumberLength(winningNumbers.size());

                return winningNumbers;
            } catch (UserInputException e) {
                System.out.println(ErrorMsg.ERROR_LOTTO_NUMBERS_SIZE_MAX.getMsg());
            }
            String retryUserInput = Console.readLine();
            winningInputNumbers = retryUserInput;
        }
    }

    public Map<Integer, Integer> topCount(List<Map<Integer, Integer>> lottoMatchCount) {
        return lottoMatchCount.stream()
                .max(Comparator.comparing(map -> map.keySet().stream().max(Integer::compareTo).orElse(0)))
                .orElse(Collections.emptyMap());
    }

    private void validateBonusNumberCheck(List<Integer> lottoNumbers, int commonInput) {
        if (lottoNumbers.contains(commonInput)) {
            throw new UserInputException(ErrorMsg.ERROR_LOTTO_DUPLICATES_NUMBERS.getMsg());
        }
    }

    private void validateDuplicateWinningNumber(int originLength, int afterLength) {
        if (originLength != afterLength) {
            throw new UserInputException(ErrorMsg.ERROR_LOTTO_DUPLICATES_NUMBERS.getMsg());
        }
    }

    private void validateWinningNumberLength(int lottoNumberLength) {
        if (lottoNumberLength != LOTTO_SIZE_MAX_LENGTH) {
            throw new UserInputException(ErrorMsg.ERROR_LOTTO_NUMBERS_SIZE_MAX.getMsg());
        }
    }
}
