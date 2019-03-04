/*
 * Bach - Java Shell Builder
 * Copyright (C) 2019 Christian Stein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ActionTests {

  @ParameterizedTest
  @EnumSource(Bach.Action.Default.class)
  void performActionOnEmptyDirectory(Bach.Action.Default action, @TempDir Path empty) {
    var bach = new Bach(true, empty);
    if (action.action == null) {
      assertThrows(NullPointerException.class, () -> action.perform(bach));
      return;
    }
    assertDoesNotThrow(() -> action.perform(bach));
    var arguments = new ArrayDeque<>(List.of("a", "z"));
    assertSame(action, action.consume(arguments));
    assertEquals("[a, z]", arguments.toString());
  }

  @Test
  void toolConsumesArgumentsAndCreatesNewActionInstance() {
    var action = Bach.Action.Default.TOOL;
    var arguments = new ArrayDeque<>(List.of("a", "z"));
    assertNotSame(action, action.consume(arguments));
    assertEquals("[]", arguments.toString());
  }
}
