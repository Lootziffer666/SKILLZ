package com.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuilderScreen(skillId: String?) {
    val clipboardManager = LocalClipboardManager.current
    val skill = SkillRepository.skills.find { it.id == skillId }

    val markdownContent = if (skill != null) {
        """
        ---
        name: "${skill.repoName.lowercase().replace(" ", "-")}"
        description: >-
          ${skill.description}
        language: "${skill.language}"
        category: "${skill.category}"
        author: "${skill.author}"
        ---
        
        # ${skill.repoName}
        
        This skill implements the following functionality:
        ${skill.description}
        
        ## Usage Guidelines
        
        - Use this skill when the user requests functionality related to ${skill.category}.
        - Ensure compatibility with ${skill.language}.
        - Follow the instructions below to implement or integrate this skill.
        
        ## Implementation Details
        
        [Details for this specific skill goes here based on implementation]
        """.trimIndent()
    } else {
        "Please select a skill from the library to build its SKILL.md template."
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("SKILL.md Builder", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            if (skill != null) {
                FloatingActionButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(markdownContent))
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Create, contentDescription = "Copy Markdown")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (skill != null) {
                Text(
                    text = "Generated SKILL.md template for ${skill.repoName}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(16.dp)
            ) {
                SelectionContainer {
                    Text(
                        text = markdownContent,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}
