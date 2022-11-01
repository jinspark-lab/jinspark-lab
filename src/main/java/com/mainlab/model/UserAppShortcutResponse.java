package com.mainlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAppShortcutResponse implements Serializable {
    private static final long serialVersionUID = 3564874002105961133L;

    private List<UserAppShortcut> userAppShortcutList;
}
