package cn.liujinnan.tools.utils.markdown;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * markdown 转 html
 * @author ljn
 * @version 1.0
 * @date 2024-04-23 16:42
 */
@Slf4j
public class MdToHtmlUtil {

    private final static MutableDataSet OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
                    // 自定义扩展，为<pre>标签添加line-numbers的class，用于prism库代码左侧行号展示
                    CodePreLineNumbersExtension.create(),
                    AutolinkExtension.create(),
                    EmojiExtension.create(),
                    StrikethroughExtension.create(),
                    TaskListExtension.create(),
                    TablesExtension.create(),
                    TocExtension.create()
            ))
            // set GitHub table parsing options
            .set(TablesExtension.WITH_CAPTION, false)
            .set(TablesExtension.COLUMN_SPANS, false)
            .set(TablesExtension.MIN_HEADER_ROWS, 1)
            .set(TablesExtension.MAX_HEADER_ROWS, 1)
            .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
            .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
            .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
            // setup emoji shortcut options
            // uncomment and change to your image directory for emoji images if you have it setup
            .set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.GITHUB)
            .set(EmojiExtension.USE_IMAGE_TYPE, EmojiImageType.IMAGE_ONLY);

    public static String mdToHtml(String mdPath) {
        try {
            Parser parser = Parser.builder(OPTIONS).build();
            URL url = Objects.requireNonNull(MdToHtmlUtil.class.getResource(mdPath));
            Document document = parser.parseReader(new InputStreamReader(url.openStream()));
            HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();
            String html = renderer.render(document);

            URL htmlTmpUrl = Objects.requireNonNull(MdToHtmlUtil.class.getResource("/md.html"));
            InputStreamReader reader = new InputStreamReader(htmlTmpUrl.openStream());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String htmlTmp = bufferedReader.lines().collect(Collectors.joining("\n"));
            return htmlTmp.replace("#{content}", html);
        } catch (Exception e) {
            log.error("Markdown conversion to HTML failed", e);
        }

        return "";
    }
}
