package com.hjy.qanda.service;

import com.hjy.qanda.model.CheckNextQuestionRes;
import com.hjy.qanda.model.ProcessResp;
import com.hjy.qanda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private SlotPointerHolder slotPointerHolder;

    public Question getNext(String slotId) {
        return slotPointerHolder.getSlotNext(Integer.parseInt("0"));
    }

    public Question getCur(String slotId) {
        return slotPointerHolder.getSlotCur(Integer.parseInt("0"));
    }

    public void markQuestion(String slotId, String qId) {
        slotPointerHolder.mark(Integer.parseInt("0"), qId);
    }

    public CheckNextQuestionRes check(String slotId) {
        return slotPointerHolder.check(Integer.parseInt(slotId));
    }

    public void refreshSlot(String slotId) {
        slotPointerHolder.refresh(Integer.parseInt(slotId));
    }

    public ProcessResp getProc(String slotId) {
        return slotPointerHolder.getProc(Integer.parseInt(slotId));
    }
}
